package com.thien.quanlychitieu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.Holder> {
    private final List<Expense> list;
    public ExpenseAdapter(List<Expense> list) { this.list = list; }

    @NonNull
    @Override public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new Holder(v);
    }

    @Override public void onBindViewHolder(@NonNull Holder h, int position) {
        Expense e = list.get(position);
        h.title.setText(e.getTitle());
        h.amount.setText(NumberFormat.getNumberInstance(new Locale("vi","VN")).format(e.getAmount()) + " đ");
        h.category.setText("Danh mục: " + e.getCategory() + " • " + e.getDate());
        h.note.setText(e.getNote() == null || e.getNote().isEmpty() ? "" : "Ghi chú: " + e.getNote());
    }

    @Override public int getItemCount() { return list.size(); }

    static class Holder extends RecyclerView.ViewHolder {
        TextView title, amount, category, note;
        Holder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            amount = itemView.findViewById(R.id.tvAmount);
            category = itemView.findViewById(R.id.tvCategory);
            note = itemView.findViewById(R.id.tvNote);
        }
    }
}
