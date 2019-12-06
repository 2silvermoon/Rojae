package com.the43appmart.nfc.kfc;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Aniruddha on 20/12/2017.
 */

public class AddMoneyAdapter extends RecyclerView.Adapter<AddMoneyAdapter.ViewHolder> {

    private Context context;
    private List<AddMoneyData> my_data;
    private Dialog dialog;
    private Button btnCancel;
    private TextView holderName, CardNumber, CurrentBalance;
    private Button btnAddAmt;

    public AddMoneyAdapter(Context context, List<AddMoneyData> AddMoneyData) {
        this.context = context;
        this.my_data = AddMoneyData;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        View itemView = LayoutInflater.from( parent.getContext() ).inflate( R.layout.add_money_card, parent, false );

        return new ViewHolder( itemView );
    }

    @SuppressLint("NewApi")
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.id.setText( my_data.get( position ).getId() );
//        holder.Course.setText( my_data.get( position ).getCourse() );
//        holder.Time.setText( my_data.get( position ).getTime() );
//        holder.Date.setText( my_data.get( position ).getStarting_date() );
        String CARDBRAND = my_data.get( position ).getBrand();
//                    holder.imgCardImage.setBackgroundTintList( R.drawable.master_card );
//            Resources res = getResources(); //resource handle
//            Drawable drawable = res.getDrawable(R.drawable.master_card); //new Image that was added to the res folder
//
//            holder.imgCardImage.setBackground(drawable);
        if (CARDBRAND.equals( "VISA" )) {
//            holder.imgCardImage.setBackgroundDrawable( R.drawable.master_card );
        }
        holder.CardHolerName.setText( my_data.get( position ).getName() );
        holder.CardNumber.setText( my_data.get( position ).getCardNumber() );
        holder.Expiry.setText( "Exp-" + my_data.get( position ).getExpiry() );
        if (my_data.get( position ).getBrand().equals( "VISA" )) {
            holder.imgCardImage.setImageResource( R.drawable.visa_card );
        }
    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView CardHolerName, CardNumber, CVV, Expiry, Brand;
        public ImageView imgCardImage;
        public Button btnAddMoney;

        public ViewHolder(final View itemView) {
            super( itemView );
            imgCardImage = (ImageView) itemView.findViewById( R.id.imgCardImage );
            CardHolerName = (TextView) itemView.findViewById( R.id.txtCardHolderName );
            CardNumber = (TextView) itemView.findViewById( R.id.txtCardNumber );

            Expiry = (TextView) itemView.findViewById( R.id.txtExpiry );
        }
    }
}
