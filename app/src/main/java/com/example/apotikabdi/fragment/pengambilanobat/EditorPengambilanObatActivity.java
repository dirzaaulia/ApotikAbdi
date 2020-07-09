package com.example.apotikabdi.fragment.pengambilanobat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.api.ApiClient;
import com.example.apotikabdi.api.ApiInterface;
import com.example.apotikabdi.api.SdkConfig;
import com.example.apotikabdi.model.Pasien;
import com.example.apotikabdi.model.Resep;
import com.google.android.material.textfield.TextInputEditText;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.LocalDataHandler;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.UserAddress;
import com.midtrans.sdk.corekit.models.UserDetail;
import com.midtrans.sdk.corekit.models.snap.Authentication;
import com.midtrans.sdk.corekit.models.snap.CreditCard;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPengambilanObatActivity extends AppCompatActivity implements EditorPengambilanObatView,TransactionFinishedCallback {

    AutoCompleteTextView autoCompleteNamaObat;
    TextInputEditText editTextIDResep, editTextNamaPasien, editTextTanggal, editTextSubTotal, editTextJumlahTunai, editTextKembaliTunai;
    Button buttonBayarOnline, buttonBayarTunai;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    Executor executor;
    BiometricPrompt biometricPrompt, biometricPrompt2;
    BiometricPrompt.PromptInfo promptInfo, promptInfo2;

    ArrayAdapter<String> adapter;
    String[] namaObatListString;
    String id, id_resep, nama_pasien, tanggal, sub_total, int_sub_total;
    List<Resep> resepList;
    List<Pasien> pasienList;

    EditorPengambilanObatPresenter editorPengambilanObatPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_pengambilan_obat);

        bindViews();

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Pengambilan Obat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Mohon tunggu, data sedang diperbaharui...");

        editorPengambilanObatPresenter = new EditorPengambilanObatPresenter(this);

        setDataFromIntentExtra();
        ambilDetailResep(id_resep);

        initMidtransSdk();

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(EditorPengambilanObatActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Autentikasi Gagal : " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                initActionButtons();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autentikasi Tidak Berhasil! Coba Ulangi Autentikasi Kembali",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentikasi Pembayaran")
                .setSubtitle("Autentikasi sidik jari untuk melanjutkan pembayaran")
                //.setNegativeButtonText("Batal")
                .setDeviceCredentialAllowed(true)
                .build();

        buttonBayarOnline.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));

        biometricPrompt2 = new BiometricPrompt(EditorPengambilanObatActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),
                        "Autentikasi Gagal : " + errString, Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                //Toast.makeText(getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
                //ubahDataPengambilanObat();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Autentikasi Tidak Berhasil! Coba Ulangi Autentikasi Kembali",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo2 = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autentikasi Pembayaran")
                .setSubtitle("Autentikasi sidik jari untuk melanjutkan pembayaran")
                //.setNegativeButtonText("Batal")
                .setDeviceCredentialAllowed(true)
                .build();

        buttonBayarTunai.setOnClickListener(v -> ubahDataPengambilanObat());
    }

    private TransactionRequest initTransactionRequest() {

        UserDetail userDetail = LocalDataHandler.readObject("user_details", UserDetail.class);

        if (userDetail == null){
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<Pasien>> call = apiInterface.getDetailPasien(nama_pasien);
            call.enqueue(new Callback<List<Pasien>>() {
                @Override
                public void onResponse(@NonNull Call<List<Pasien>> call, @NonNull Response<List<Pasien>> response) {
                    pasienList = response.body();

                    if (pasienList != null && pasienList.size() > 0) {
                        //namaObatListString = new String[pasienList.size()];

                        for (int i = 0; i < pasienList.size(); i++) {
                            //namaObatListString[i] = pasienList.get(i).getNama();

                            //adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaObatListString);
                            //autoCompleteNamaObat.setAdapter(adapter);
                            UserDetail userDetail = new UserDetail();
                            userDetail.setUserFullName(pasienList.get(i).getNama());
                            userDetail.setEmail(pasienList.get(i).getEmail());
                            userDetail.setPhoneNumber(pasienList.get(i).getNohp());
                            //userDetail.setUserId("bangtiray-6789");

                            ArrayList<UserAddress> userAddresses = new ArrayList<>();
                            UserAddress userAddress = new UserAddress();
                            userAddress.setAddress(pasienList.get(i).getAlamat());
                            userAddress.setCity("Medan");
                            userAddress.setAddressType(com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH);
                            userAddress.setZipcode(pasienList.get(i).getKode_pos());
                            userAddress.setCountry("IDN");
                            userAddresses.add(userAddress);
                            userDetail.setUserAddresses(userAddresses);
                            LocalDataHandler.saveObject("user_details", userDetail);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Pasien>> call, @NonNull Throwable t) {
                    Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data pasien", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<List<Pasien>> call = apiInterface.getDetailPasien(nama_pasien);
            call.enqueue(new Callback<List<Pasien>>() {
                @Override
                public void onResponse(@NonNull Call<List<Pasien>> call, @NonNull Response<List<Pasien>> response) {
                    pasienList = response.body();

                    if (pasienList != null && pasienList.size() > 0) {
                        //namaObatListString = new String[pasienList.size()];

                        for (int i = 0; i < pasienList.size(); i++) {
                            //namaObatListString[i] = pasienList.get(i).getNama();

                            //adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaObatListString);
                            //autoCompleteNamaObat.setAdapter(adapter);
                            UserDetail userDetail = new UserDetail();
                            userDetail.setUserFullName(pasienList.get(i).getNama());
                            userDetail.setEmail(pasienList.get(i).getEmail());
                            userDetail.setPhoneNumber(pasienList.get(i).getNohp());
                            //userDetail.setUserId("bangtiray-6789");

                            ArrayList<UserAddress> userAddresses = new ArrayList<>();
                            UserAddress userAddress = new UserAddress();
                            userAddress.setAddress(pasienList.get(i).getAlamat());
                            userAddress.setCity("Medan");
                            userAddress.setAddressType(com.midtrans.sdk.corekit.core.Constants.ADDRESS_TYPE_BOTH);
                            userAddress.setZipcode(pasienList.get(i).getKode_pos());
                            userAddress.setCountry("IDN");
                            userAddresses.add(userAddress);
                            userDetail.setUserAddresses(userAddresses);
                            LocalDataHandler.saveObject("user_details", userDetail);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Pasien>> call, @NonNull Throwable t) {
                    Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data pasien", Toast.LENGTH_SHORT).show();
                }
            });

        }

        TransactionRequest transactionRequestNew = new TransactionRequest(System.currentTimeMillis() + "", Double.parseDouble(int_sub_total));

        //transactionRequestNew.setCustomerDetails(initCustomerDetails());

        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();

        ItemDetails itemDetails = new ItemDetails("1", Double.parseDouble(int_sub_total), 1, "Resep Obat");
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);

        CreditCard creditCardOptions = new CreditCard();
        // Set to true if you want to save card as one click
        creditCardOptions.setSaveCard(false);
        creditCardOptions.setAuthentication(Authentication.AUTH_NONE);
        // Set to true to save card token as `one click` token
        // Set Credit Card Options
        transactionRequestNew.setCreditCard(creditCardOptions);

        MidtransSDK.getInstance().setTransactionRequest(transactionRequestNew);

        return transactionRequestNew;
    }

    /*
    private CustomerDetails initCustomerDetails() {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setPhone("081234567890");
        customerDetails.setFirstName("Dirza");
        customerDetails.setEmail("dirza@gmail.com");
        customerDetails.setBillingAddress(initBillingAddress());

        return customerDetails;
    }

    private BillingAddress initBillingAddress(){
        BillingAddress billingAddress = new BillingAddress();
        billingAddress.setFirstName("Dirza");
        billingAddress.setLastName("Aulia");
        billingAddress.setAddress("Jl. Tubagus Ismail Raya");
        billingAddress.setCity("Bandung");

        return billingAddress;
    }
     */

    private void initMidtransSdk() {
        String client_key = SdkConfig.MERCHANT_CLIENT_KEY;
        String base_url = SdkConfig.MERCHANT_BASE_CHECKOUT_URL;

        SdkUIFlowBuilder.init()
                .setClientKey(client_key) // client_key is mandatory
                .setContext(this) // context is mandatory
                .setTransactionFinishedCallback(this) // set transaction finish callback (sdk callback)
                .setMerchantBaseUrl(base_url) //set merchant url
                .enableLog(true) // enable sdk log
                .buildSDK();
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    //Transaksi pembayasan selesai. ID: " + result.getResponse().getTransactionId()
                    String pesanSukses = "Transaksi pembayaran selesai.";
                    Toast.makeText(this, pesanSukses, Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    //Transaksi pembayaran tertunda / pending. ID: " + result.getResponse().getTransactionId()
                    String pesanPending = "Transaksi pembayaran tertunda / pending";
                    Toast.makeText(this, pesanPending, Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    //Transaksi pembayaran gagal. ID: " + result.getResponse().getTransactionId() + ". Pesan : " + result.getResponse().getStatusMessage()
                    String pesanGagal = "Transaksi pembayaran gagal";
                    Toast.makeText(this, pesanGagal, Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaksi pembayaran dibatalkan", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaksi pembayaran invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaksi pembayaran selesai dengan adanya kesalahan.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initActionButtons() {

        MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
        MidtransSDK.getInstance().startPaymentUiFlow(EditorPengambilanObatActivity.this);
    }

    private void ambilDetailResep(String id) {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Resep>> call = apiInterface.getDetailResep(id);
        call.enqueue(new Callback<List<Resep>>() {
            @Override
            public void onResponse(@NonNull Call<List<Resep>> call, @NonNull Response<List<Resep>> response) {
                resepList = response.body();

                if (resepList != null && resepList.size() > 0) {
                    namaObatListString = new String[resepList.size()];

                    for (int i = 0; i < resepList.size(); i++) {
                        namaObatListString[i] = resepList.get(i).getNama_obat();

                        adapter = new ArrayAdapter<>(getBaseContext(), R.layout.dropdown_menu, namaObatListString);
                        autoCompleteNamaObat.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Resep>> call, @NonNull Throwable t) {
                Toast.makeText(getBaseContext(), "Terjadi kesalahan saat mengambil data resep", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ubahDataPengambilanObat(){
        editorPengambilanObatPresenter.ubahPengambilanObat(id);
    }

    private void bindViews() {
        autoCompleteNamaObat = findViewById(R.id.auto_complete_nama_obat);
        editTextIDResep = findViewById(R.id.edit_text_id_resep_pengambilan_obat);
        editTextNamaPasien = findViewById(R.id.edit_text_nama_pasien_pengambilan_obat);
        editTextTanggal = findViewById(R.id.edit_text_tanggal_pengambilan_obat);
        editTextSubTotal = findViewById(R.id.edit_text_subtotal_pengambilan_obat);
        editTextJumlahTunai = findViewById(R.id.edit_text_subtotal_pembayaran_tunai);
        editTextKembaliTunai = findViewById(R.id.edit_text_kembalian_pembayaran_tunai);
        buttonBayarOnline = findViewById(R.id.button_bayar_online);
        buttonBayarTunai = findViewById(R.id.button_bayar_tunai);
        toolbar = findViewById(R.id.toolbar_editor_pengambilan_obat);
    }

    private void setDataFromIntentExtra() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        id_resep = intent.getStringExtra("id_resep");
        nama_pasien = intent.getStringExtra("nama_pasien");
        tanggal = intent.getStringExtra("tanggal");
        sub_total = intent.getStringExtra("sub_total");
        int_sub_total = intent.getStringExtra("int_sub_total");

        /*
        String strCurrentDate = tanggal;
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd", new Locale("id", "ID"));
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd/MMM/yyyy", new Locale("id", "ID"));
        tanggal = format.format(newDate);
         */

        if (id_resep != null) {
            editTextIDResep.setText(id_resep);
            editTextNamaPasien.setText(nama_pasien);
            editTextTanggal.setText(tanggal);
            editTextSubTotal.setText(sub_total);
        }
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        //finish();
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
