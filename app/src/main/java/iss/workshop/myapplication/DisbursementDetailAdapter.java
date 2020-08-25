package iss.workshop.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.myapplication.Model.DisbursementDetailAPImodel;


public class DisbursementDetailAdapter extends ArrayAdapter<DisbursementDetailAPImodel> {
    Context context;
    private List<DisbursementDetailAPImodel> Listofdisbursementdetail = new ArrayList<DisbursementDetailAPImodel>();

    public DisbursementDetailAdapter(@NonNull Context context, List<DisbursementDetailAPImodel> Listofdisbursementdetail) {
        super(context, 0, Listofdisbursementdetail);
        this.context = context;
        this.Listofdisbursementdetail =  Listofdisbursementdetail;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View retrievalRow = convertView;
        if (retrievalRow == null) {
            retrievalRow = LayoutInflater.from(context).inflate(R.layout.disbursementdetail_row, parent, false);
        }
        final DisbursementDetailAPImodel currdisdetail = Listofdisbursementdetail.get(position);

        TextView itemcode= (TextView) retrievalRow.findViewById(R.id.itemcodebox);
        if(itemcode!=null){
            itemcode.setText(currdisdetail.getInventoryItemId());
        }

        TextView Description= (TextView) retrievalRow.findViewById(R.id.descriptionbox);
        if(Description!=null){
            Description.setText(currdisdetail.getItemDescription());
        }
        TextView qty= (TextView) retrievalRow.findViewById(R.id.qtybox);
        if(qty!=null){
            qty.setText(String.valueOf(currdisdetail.getQtyNeeded()));
        }

        TextView receivedqty= (TextView) retrievalRow.findViewById(R.id.receivedqtybox);
        if(receivedqty!=null){
                receivedqty.setText(String.valueOf(currdisdetail.getQtyReceived()));
        }

        return retrievalRow;
    }
}