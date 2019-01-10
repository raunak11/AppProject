package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.example.raunak.project1.R;
import java.util.List;

import Database.DataBaseHandler;
import Model.User;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context context;
    private List<User> list;

    public RecyclerAdapter(Context context, List<User> users) {
        this.context = context;
        this.list = users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.linear,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User user = list.get(i);
        viewHolder.name.setText(user.getName());
        viewHolder.email.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {
        public TextView name, email;
        public Button delbutton;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            context = ctx;
            name = itemView.findViewById(R.id.textView1);
            email = itemView.findViewById(R.id.textView2);
            delbutton = itemView.findViewById(R.id.delbutton);

            delbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = getAdapterPosition();
                    User user = list.get(num);
                    deleteItem(user.getId());
                }
            });
        }
        private void deleteItem(int id) {
            DataBaseHandler db= new DataBaseHandler(context);
            db.deleteUser(id);
            list.remove(getAdapterPosition());
            notifyItemRemoved(getAdapterPosition());
        }
    }
}