package com.thien.quanlychitieu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    ArrayList<Expense> list = new ArrayList<>();
    ExpenseAdapter adapter;
    TextView tvTotal;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        tvTotal = findViewById(R.id.tvTotal);

        if (auth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        RecyclerView rv = findViewById(R.id.recyclerExpenses);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(list);
        rv.setAdapter(adapter);

        findViewById(R.id.btnAdd).setOnClickListener(v ->
                startActivity(new Intent(this, AddExpenseActivity.class)));

        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    @Override protected void onResume() {
        super.onResume();
        if (auth != null && auth.getCurrentUser() != null) loadExpenses();
    }

    private void loadExpenses() {
        String uid = auth.getCurrentUser().getUid();
        db.collection("users").document(uid).collection("expenses")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(result -> {
                    list.clear();
                    double total = 0;
                    for (var doc : result.getDocuments()) {
                        Expense e = doc.toObject(Expense.class);
                        if (e != null) {
                            e.setId(doc.getId());
                            list.add(e);
                            total += e.getAmount();
                        }
                    }
                    adapter.notifyDataSetChanged();
                    tvTotal.setText("Tổng chi: " +
                            NumberFormat.getNumberInstance(new Locale("vi","VN")).format(total) + " đ");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Không tải được dữ liệu: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }
}
