package com.dictionary.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.dictionary.Model.Word;
import com.dictionary.R;
import com.dictionary.ui.adapters.ResultViewAdapter;
import com.dictionary.util.ComFunction;
import com.dictionary.util.DB_helper;
import com.dictionary.util.Dac;
import com.dictionary.util.MySharedPreference;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {
    MaterialSearchView searchView;
    Toolbar toolbar;
    String searchWord;
    List<Word> ws = null;
    static List<Word> recentList;
    RecyclerView rvResults;
    TextView no_result_text;
    Word w;
    EditText mSearchSrcTextView;
    Word recent_Word;
    ResultViewAdapter adapter;
    ResultViewAdapter adapter_recent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appbarlayout);

        //Adding DB in here
        final DB_helper db = new DB_helper(getApplicationContext());
        if (MySharedPreference.getInstance(this).getStringPreference("DB_EXISTS", "").equals("")) {
            db.MakeDB();
            MySharedPreference.getInstance(this).setStringPreference("DB_EXISTS", "true");
        }

        //Set Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rvResults = (RecyclerView) findViewById(R.id.rvResult);
        no_result_text = (TextView) findViewById(R.id.txtNoResult);
        no_result_text.setText(R.string.noResultText);

        final SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = pref.edit();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setSubtitleTextColor(Color.WHITE);
        }

        mSearchSrcTextView = (EditText) findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView);
        mSearchSrcTextView.setKeyListener(DigitsKeyListener.getInstance("abcdefghijklmnopqrestuvwxyz"));
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                searchWord = newText;

                if (searchWord.length() != 0) {
                    //Get Table Name
                    char firstChar = searchWord.charAt(0);
                    ComFunction com = new ComFunction();
                    String table_name = com.gettableName(firstChar);
                    Word w = new Word();
                    w.setWord(searchWord);
                    Dac dac = new Dac();
                    ws = dac.instance(getApplicationContext()).getLikelyWords(w, table_name);
                    if (ws.size() != 0) {
                        no_result_text.setText("");
                        adapter = new ResultViewAdapter(ws);
                    } else {
                        no_result_text.setText(R.string.noResultText);
                        ws = new ArrayList<>();
                        adapter = new ResultViewAdapter(ws);
                    }
                } else {
                    no_result_text.setText(R.string.noResultText);
                    ws = new ArrayList<>();
                    adapter = new ResultViewAdapter(ws);
                }

                rvResults.setAdapter(new SlideInLeftAnimationAdapter(adapter));
                adapter.setOnItemClickListener(new ResultViewAdapter.OnItemClickListener() {
                                                   @Override
                                                   public void onItemClick(View view, int position) {
                                                       w = ws.get(position);
                                                       //Going To detail view
                                                       Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                                                       intent.putExtra("word", w.getWord());
                                                       intent.putExtra("wordDesc", w.getDesc());
                                                       intent.putExtra("wordType", w.getWordType());
                                                       intent.putExtra("fav", w.getFav());
                                                       Bundle args = new Bundle();
                                                       args.putString("word", w.getWord());

                                                       //Saving Preferences
                                                       if (w != null) {
                                                           editor.putString("recentItem", pref.getString("recentItem", "") + "*" + w.getWord());
                                                           editor.commit();
                                                       }

                                                       startActivity(intent);
                                                   }
                                               }
                );
                return false;
            }
        });

        rvResults.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
                                               @Override
                                               public void onSearchViewShown() {
                                                   //Do some magic
                                               }

                                               @Override
                                               public void onSearchViewClosed() {
                                                   //Do some magic
                                                   toolbar.setTitle(R.string.app_name);
                                                   no_result_text.setText(R.string.noResultText);
                                                   ws = new ArrayList<>();
                                                   adapter = new ResultViewAdapter(ws);
                                                   rvResults.setAdapter(new SlideInLeftAnimationAdapter(adapter));
                                               }
                                           }

        );

        //Adding Font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/RobotoCondensed.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    // onCreateOptionsMenu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    //Remove Duplicated String
    public String[] removeDuplicatedWords(String[] haveDuplicate) {
        List<String> listWord = Arrays.asList(haveDuplicate);
        Set<String> listSet = new LinkedHashSet<>(listWord);

        String[] nonDuplicate = new String[listSet.size()];
        nonDuplicate = listSet.toArray(nonDuplicate);
        return nonDuplicate;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        switch (item.getItemId()) {
            case R.id.recent_search:
                //Start
                toolbar.setTitle("Recent Search");
                recentList = new ArrayList<>();
                if (pref.getString("recentItem", "").length() != 0) {
                    String longString = pref.getString("recentItem", "");
                    String[] temps = longString.split("\\*");
                    String[] resultString = removeDuplicatedWords(temps);

                    for (int i = 1; i < resultString.length; i++) {
                        Word obj = new Word(resultString[i]);
                        recentList.add(obj);
                    }
                }

                if (recentList.size() != 0) {
                    no_result_text.setText("");
                    adapter_recent = new ResultViewAdapter(recentList);
                    rvResults.setAdapter(new SlideInLeftAnimationAdapter(adapter_recent));
                    adapter_recent.setOnItemClickListener(new ResultViewAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            recent_Word = recentList.get(position);
                            Dac dac = new Dac();
                            Word resultWord = dac.instance(getApplicationContext()).getByWord(recent_Word.getWord());
                            //Going To detail view
                            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                            intent.putExtra("word", resultWord.getWord());
                            intent.putExtra("wordDesc", resultWord.getDesc());
                            intent.putExtra("wordType", resultWord.getWordType());
                            intent.putExtra("fav", resultWord.getFav());
                            Bundle args = new Bundle();
                            args.putString("word", resultWord.getWord());
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(this, "No Recent Search", Toast.LENGTH_SHORT).show();
                }
                //End
                break;
            case R.id.about_us:
                Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent);
                break;
            case R.id.licensed_credits:
                WebView webView = new WebView(this);
                webView.loadUrl("file:///android_asset/licensed.html");
                boolean wrapInScrollView = true;
                new MaterialDialog.Builder(this)
                        .title("Credits")
                        .customView(webView, wrapInScrollView)
                        .positiveText("OK")
                        .positiveColorRes(R.color.primary_dark)
                        .build()
                        .show();
                break;
            case R.id.clearRecent:
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().commit();
                if (recentList.size() != 0) {
                    recentList = new ArrayList<>();
                    adapter = new ResultViewAdapter(recentList);
                    rvResults.setAdapter(new SlideInLeftAnimationAdapter(adapter));
                    Toast.makeText(this, "Clear Search", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No Recent Search", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        return true;
    }

    //Handle the response
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //OnBackPressed
    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }
}
