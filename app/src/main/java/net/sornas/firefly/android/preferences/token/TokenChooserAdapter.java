package net.sornas.firefly.android.preferences.token;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import net.sornas.firefly.R;

import java.util.List;

public class TokenChooserAdapter extends RecyclerView.Adapter<TokenChooserAdapter.TokenChooserViewHolder> {

    private List<Token> dataset;

    public TokenChooserAdapter(List<Token> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public TokenChooserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.token_chooser_view, parent, false);

        return new TokenChooserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TokenChooserViewHolder holder, int position) {
        Token token = dataset.get(position);

        holder.textViewName.setText(token.getName());
        holder.textViewURL.setText(token.getUrl());
        holder.radioButtonActive.setChecked(token.isActive());

        holder.radioButtonActive.setOnClickListener(v -> {
            if (token.isActive()) return;
            for (Token aToken: dataset) {
                if (aToken.isActive()) {
                    aToken.setActive(false);
                    notifyItemChanged(dataset.indexOf(aToken));
                }
            }
            token.setActive(true);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class TokenChooserViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewURL;
        public RadioButton radioButtonActive;

        public TokenChooserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textView_name);
            textViewURL = itemView.findViewById(R.id.textView_url);
            radioButtonActive = itemView.findViewById(R.id.radioButton_active);
        }
    }
}
