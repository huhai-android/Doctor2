package com.newdjk.doctor.ui.activity.IM;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.newdjk.doctor.R;
import com.tencent.TIMAddFriendRequest;
import com.tencent.TIMFriendResult;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFriendActivity extends AppCompatActivity {

    @BindView(R.id.identifier)
    EditText identifier;
    @BindView(R.id.btn_search)
    Button btn_search;
    @BindView(R.id.add_user)
    LinearLayout add_user;
    @BindView(R.id.nick_name)
    TextView nick_name;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.no_user)
    RelativeLayout no_user;
    private String mIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
      /*  identifier = findViewById(R.id.identifier);
        btn_search = findViewById(R.id.btn_search);
        add_user = findViewById(R.id.add_user);
        nick_name = findViewById(R.id.nick_name);
        btn_add = findViewById(R.id.btn_add);
        no_user = findViewById(R.id.no_user);*/
       ButterKnife.bind(this);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = identifier.getText().toString();
                if (!TextUtils.isEmpty(id)) {
                    identifier.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(identifier.getWindowToken(), 0);
                    List<String> list = new ArrayList<>();
                  /*  if (StringUtil.isNumeric(id)) {
                        id = "86-" + id;
                    }*/
                    list.add(id);
                    TIMFriendshipManager.getInstance().getUsersProfile(list, new TIMValueCallBack<List<TIMUserProfile>>() {
                        @Override
                        public void onError(int i, String s) {
                            no_user.setVisibility(View.VISIBLE);
                            add_user.setVisibility(View.GONE);
                        }

                        @Override
                        public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                            if (timUserProfiles.size() == 0) {
                                no_user.setVisibility(View.VISIBLE);
                                add_user.setVisibility(View.GONE);
                            } else {
                                TIMUserProfile timUserProfile = timUserProfiles.get(0);
                                mIdentifier = timUserProfile.getIdentifier();
                              //  nick_name.setText(timUserProfile.getNickName());
                                nick_name.setText(mIdentifier);
                                no_user.setVisibility(View.GONE);
                                add_user.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                }
            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TIMAddFriendRequest> list = new ArrayList<>();
                TIMAddFriendRequest timAddFriendRequest = new TIMAddFriendRequest();
                timAddFriendRequest.setIdentifier(mIdentifier);
                list.add(timAddFriendRequest);
                TIMFriendshipManager.getInstance().addFriend(list, new TIMValueCallBack<List<TIMFriendResult>>() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess(List<TIMFriendResult> timFriendResults) {
                        Toast.makeText(AddFriendActivity.this, "请求已发送", Toast.LENGTH_SHORT).show();
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
                    }
                });
            }
        });
    }
}
