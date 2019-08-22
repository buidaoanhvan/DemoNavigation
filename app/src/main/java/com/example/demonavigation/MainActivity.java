package com.example.demonavigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.demonavigation.Fragment.CartFragment;
import com.example.demonavigation.Fragment.GiftsFragment;
import com.example.demonavigation.Fragment.ProfileFragment;
import com.example.demonavigation.Fragment.StoreFragment;
import com.example.demonavigation.model.SinhVien;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        StoreFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener,
        CartFragment.OnFragmentInteractionListener,
        GiftsFragment.OnFragmentInteractionListener {
    private ActionBar toolbar;
    private EditText tensinhvien, masinhvien;
    private TextView tvdatasv;
    private static final String TAG = "MainActivity";
    private static final String KEY_TENSV = "tensv";
    private static final String KEY_MASV = "masv";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference sinhviendb= db.collection("QuanLySinhVien");
    private DocumentReference documentReference = db.document("QuanLySinhVien/IOT13301");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tensinhvien = (EditText) findViewById(R.id.et_TenSv);
        masinhvien = (EditText) findViewById(R.id.et_maSinhVien);
        tvdatasv = (TextView) findViewById(R.id.tv_loadData);
        toolbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNaviga);
        toolbar.setTitle("Shop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        sinhviendb.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    SinhVien note = documentSnapshot.toObject(SinhVien.class);
                    note.setDocumentId(documentSnapshot.getId());

                    String ID = note.getDocumentId();
                    String ten = note.getTen();
                    String ma = note.getMa();

                    data += "ID: " + ID  + "\n Tên SV: " + ten + "\n Mã SV: " + ma + "\n\n";
                }
                tvdatasv.setText(data);
            }
        });
    }

    public void saveSinhvien(View view){
        String ten = tensinhvien.getText().toString();
        String ma = masinhvien.getText().toString();
        SinhVien sv = new SinhVien(ten,ma);
        sinhviendb.add(sv);
    }

    public void loadSinhVien (View view){

        sinhviendb.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                String data="";
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    SinhVien sv = documentSnapshot.toObject(SinhVien.class);
                    sv.setDocumentId(documentSnapshot.getId());

                    String ID = sv.getDocumentId();
                    String ten = sv.getTen();
                    String ma = sv.getMa();

                    data += "ID: " + ID  + "\n Tên SV: " + ten + "\n Mã SV: " + ma + "\n\n";
                }
                tvdatasv.setText(data);
            }
        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNaviga = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_shop:
                    toolbar.setTitle("Shop");
                    fragment = new StoreFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_gifts:
                    toolbar.setTitle("gifts");
                    fragment = new GiftsFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_cart:
                    toolbar.setTitle("cart");
                    fragment = new CartFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_profile:
                    toolbar.setTitle("profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
