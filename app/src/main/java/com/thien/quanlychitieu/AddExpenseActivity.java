package com.thien.quanlychitieu;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExpenseActivity extends AppCompatActivity {
    EditText edtTitle, edtAmount, edtNote;
    Spinner spinner;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        edtTitle = findViewById(R.id.edtTitle);
        edtAmount = findViewById(R.id.edtAmount);
        edtNote = findViewById(R.id.edtNote);
        spinner = findViewById(R.id.spinnerCategory);

        String[] categories = {"Ăn uống", "Di chuyển", "Mua sắm", "Hóa đơn", "Giải trí", "Y tế", "Khác"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories));

        findViewById(R.id.btnSave).setOnClickListener(v -> save());
        findViewById(R.id.btnCancel).setOnClickListener(v -> finish());
    }

    private void save() {
        String title = edtTitle.getText().toString().trim();
        String amountText = edtAmount.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(amountText)) {
            Toast.makeText(this, "Vui lòng nhập tên và số tiền", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (Exception e) {
            Toast.makeText(this, "Số tiền không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        Expense expense = new Expense(title, amount,
                spinner.getSelectedItem().toString(),
                edtNote.getText().toString().trim(), date);

        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).collection("expenses")
                .add(expense)
                .addOnSuccessListener(r -> {
                    Toast.makeText(this, "Đã lưu khoản chi", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Lưu thất bại: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
