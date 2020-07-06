package com.example.apotikabdi.fragment.pengambilanobat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.apotikabdi.R;
import com.example.apotikabdi.api.SdkConfig;
import com.google.android.material.textfield.TextInputEditText;
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback;
import com.midtrans.sdk.corekit.core.MidtransSDK;
import com.midtrans.sdk.corekit.core.TransactionRequest;
import com.midtrans.sdk.corekit.models.CustomerDetails;
import com.midtrans.sdk.corekit.models.ItemDetails;
import com.midtrans.sdk.corekit.models.snap.TransactionResult;
import com.midtrans.sdk.uikit.SdkUIFlowBuilder;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

public class EditorPengambilanObatActivity extends AppCompatActivity implements TransactionFinishedCallback {

    TextInputEditText editTextIDResep, editTextNamaPasien, editTextTanggal, editTextSubTotal;
    Button buttonBayar;
    Toolbar toolbar;
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    String id_resep, nama_pasien, tanggal, sub_total;

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

        setDataFromIntentExtra();

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

        buttonBayar.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }

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


    private CustomerDetails initCustomerDetails() {
        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setPhone("085310102020");
        customerDetails.setFirstName("Dirza");
        customerDetails.setEmail("dirza@gmail.com");
        return customerDetails;
    }

    private TransactionRequest initTransactionRequest() {
        TransactionRequest transactionRequestNew = new
                TransactionRequest(System.currentTimeMillis() + "", 20000);

        transactionRequestNew.setCustomerDetails(initCustomerDetails());

        ItemDetails itemDetails = new ItemDetails("1", 20000, 1, "Trekking Shoes");

        ArrayList<ItemDetails> itemDetailsArrayList = new ArrayList<>();
        itemDetailsArrayList.add(itemDetails);
        transactionRequestNew.setItemDetails(itemDetailsArrayList);

        return transactionRequestNew;
    }

    private void initActionButtons() {
        MidtransSDK.getInstance().setTransactionRequest(initTransactionRequest());
        MidtransSDK.getInstance().startPaymentUiFlow(EditorPengambilanObatActivity.this);
    }

    @Override
    public void onTransactionFinished(TransactionResult result) {
        if (result.getResponse() != null) {
            switch (result.getStatus()) {
                case TransactionResult.STATUS_SUCCESS:
                    Toast.makeText(this, "Transaction Finished. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_PENDING:
                    Toast.makeText(this, "Transaction Pending. ID: " + result.getResponse().getTransactionId(), Toast.LENGTH_LONG).show();
                    break;
                case TransactionResult.STATUS_FAILED:
                    Toast.makeText(this, "Transaction Failed. ID: " + result.getResponse().getTransactionId() + ". Message: " + result.getResponse().getStatusMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
            result.getResponse().getValidationMessages();
        } else if (result.isTransactionCanceled()) {
            Toast.makeText(this, "Transaction Canceled", Toast.LENGTH_LONG).show();
        } else {
            if (result.getStatus().equalsIgnoreCase(TransactionResult.STATUS_INVALID)) {
                Toast.makeText(this, "Transaction Invalid", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Transaction Finished with failure.", Toast.LENGTH_LONG).show();
            }
        }
    }



    private void bindViews() {

        editTextIDResep = findViewById(R.id.edit_text_id_resep_pengambilan_obat);
        editTextNamaPasien = findViewById(R.id.edit_text_nama_pasien_pengambilan_obat);
        editTextTanggal = findViewById(R.id.edit_text_tanggal_pengambilan_obat);
        editTextSubTotal = findViewById(R.id.edit_text_subtotal_pengambilan_obat);
        buttonBayar = findViewById(R.id.button_bayar);
        toolbar = findViewById(R.id.toolbar_editor_pengambilan_obat);
    }

    private void setDataFromIntentExtra() {

        Intent intent = getIntent();
        id_resep = intent.getStringExtra("id_resep");
        nama_pasien = intent.getStringExtra("nama_pasien");
        tanggal = intent.getStringExtra("tanggal");
        sub_total = intent.getStringExtra("sub_total");

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
}
