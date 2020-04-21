package com.example.apotikabdi.main;

import android.os.Bundle;

import com.example.apotikabdi.R;
import com.example.apotikabdi.fragment.antrian.AntrianFragment;
import com.example.apotikabdi.fragment.pasien.PasienFragment;
import com.example.apotikabdi.fragment.pengambilanobat.PengambilanObatFragment;
import com.example.apotikabdi.fragment.resep.ResepFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {

    TabAdapter tabAdapter;
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_main_activity);

        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new AntrianFragment(), "Antrian");
        tabAdapter.addFragment(new PasienFragment(), "Pasien");
        tabAdapter.addFragment(new ResepFragment(), "Resep");
        tabAdapter.addFragment(new PengambilanObatFragment(), "Bayar");
        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);

        int[] tabIcons = {
                R.drawable.ic_menu_antrian,
                R.drawable.ic_menu_pasien,
                R.drawable.ic_resep,
                R.drawable.ic_pengambilan_obat
        };

        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(tabIcons[0]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setIcon(tabIcons[2]);
        Objects.requireNonNull(tabLayout.getTabAt(3)).setIcon(tabIcons[3]);

    }

}
