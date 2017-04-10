package li.power.fuckidic.guesscode;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private int answer=0;
    private int maxNow=98;
    private int minNow=0;

    static EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Data> myDataset = new ArrayList<>();
        for(int i = 1; i <= 99; i++){
            myDataset.add(new Data(i,false));
        }
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleView);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        Random random=new Random();
        answer=1+random.nextInt(98);
        et=(EditText)findViewById(R.id.editText);

        Button btn=(Button)findViewById(R.id.button);
        btn.setText(String.valueOf(answer));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=et.getText().toString();
             if(isNumeric(input))
             {
                 int in=Integer.parseInt(input);
                 if(in-1>=maxNow || in-1<=minNow)
                    Toast.makeText(getApplicationContext(),"亂來R",Toast.LENGTH_SHORT).show();

                 if(in>answer)
                 {
                     for(int i=maxNow;i>in-2;i--)
                     {
                         myDataset.get(i).setBlock(true);
                     }
                     mAdapter.notifyDataSetChanged();
                     maxNow=in-1;
                 }
                 else if(in<answer)
                 {
                     for(int i=minNow;i<in;i++)
                     {
                         myDataset.get(i).setBlock(true);
                     }
                     mAdapter.notifyDataSetChanged();
                     minNow=in-1;
                 }
                 else
                 {
                     AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.MyAlertDialogStyle);
                     builder.setMessage("要不要重玩R");
                     builder.setTitle("陪我玩啦");
                     builder.setPositiveButton("重玩", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             finish();
                             startActivity(getIntent());
                         }
                     });
                     builder.setNegativeButton("離開", new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             finish();
                         }
                     });
                     AlertDialog dialog = builder.create();
                     dialog.show();
                 }
             }

            }
        });
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<Data> mData;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.textView);
            }
        }

        public MyAdapter(List<Data> data) {
            mData = data;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.button, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.mTextView.setText(String.valueOf(mData.get(position).getIndex()));
            if(mData.get(position).isBlock())
                holder.mTextView.setBackgroundColor(0x80000000);
            else
                holder.mTextView.setBackgroundColor(0xFFFFFF);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!mData.get(position).isBlock())
                        MainActivity.et.setText(String.valueOf(mData.get(position).getIndex()));
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }
}
