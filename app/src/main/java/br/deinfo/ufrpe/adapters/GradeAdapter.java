package br.deinfo.ufrpe.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.caverock.androidsvg.SVGImageView;

import java.util.List;

import br.deinfo.ufrpe.R;
import br.deinfo.ufrpe.models.Tabledatum;

/**
 * Created by paulo on 02/11/2016.
 */

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.GradeViewHolder> {
    private List<Tabledatum> mTables;
    private Context mContext;

    public GradeAdapter(List<Tabledatum> tables) {
        mTables = tables;
    }

    @Override
    public GradeAdapter.GradeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grade_table, parent, false);

        if (mContext == null)
            mContext = parent.getContext();

        return new GradeAdapter.GradeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapter.GradeViewHolder holder, int position) {
        final Tabledatum table = mTables.get(position);

        if (position == 0) {
            SVGImageView imageView = new SVGImageView(mContext);
            imageView.setImageAsset("folder.svg");
            holder.mIcon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else if (position == mTables.size() - 1) {
            ImageView imageView = new ImageView(mContext);
            imageView.setImageDrawable(mContext.getDrawable(R.drawable.agg_sum));
            holder.mIcon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            SVGImageView imageView = new SVGImageView(mContext);
            imageView.setImageAsset("assign.svg");
            holder.mIcon.addView(imageView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }

        holder.mName.setText(table.getItemname().getContent().replaceAll("\\<[^>]*>",""));
        if (table.getGrade() != null)
            holder.mGrade.setText(table.getGrade().getContent());
    }

    @Override
    public int getItemCount() {
        return mTables.size();
    }

    public class GradeViewHolder extends RecyclerView.ViewHolder {
        private TextView mName, mGrade;
        private LinearLayout mIcon;

        public GradeViewHolder(View itemView) {
            super(itemView);

            mIcon = (LinearLayout) itemView.findViewById(R.id.icon);
            mName = (TextView) itemView.findViewById(R.id.name);
            mGrade = (TextView) itemView.findViewById(R.id.grade);
        }
    }
}