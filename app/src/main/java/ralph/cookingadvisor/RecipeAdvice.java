package rcd.cookingadvisor;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import rcd.cookingadvisor.Adapters.DatabaseAdapter;
import rcd.cookingadvisor.Database.DatabaseHelper;
import rcd.cookingadvisor.Model.RecipeObjectModel;

public class RecipeAdvice extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    public static ArrayList<RecipeObjectModel> data;
    
    DatabaseHelper db ;
    
    ArrayList<String> rcp_namelist;
    ArrayList<String> rcp_executelist;
    
    List<String[]> c1list;
    List<String[]> c2list;
    List<String[]> c3list;
    List<String[]> c4list;

    LinkedHashMap<String,ArrayList<String>> c5list;
    
    List<String[]> recipes_name_list;
    List<String[]> ingredients_name_list;

    ArrayList<String> foolist;
    ArrayList<RecipeObjectModel> combinelist;
    
    SearchView searchView1;
    SearchView searchView2;
    
    int flag1=1;
    int flag2=1;
    int checker1=0;
    int checker2=0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_advice);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        db= new DatabaseHelper(this);
        searchView1 = (SearchView) findViewById(R.id.searchView1);
        searchView1.setQueryHint("Search Here");
        searchView1.setQueryRefinementEnabled(true);
        searchView2 = (SearchView) findViewById(R.id.searchView2);
        searchView2.setQueryHint("Search Here");
        searchView2.setQueryRefinementEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = new ArrayList<RecipeObjectModel>();
        fetchData();

        combinelist = new ArrayList<RecipeObjectModel>();
        
        final ArrayList<RecipeObjectModel> filteredList1 = new ArrayList<RecipeObjectModel>();
        final ArrayList<RecipeObjectModel> filteredList2 = new ArrayList<RecipeObjectModel>();
        
        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            
            @Override
            public boolean onQueryTextSubmit(String query) {return  false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredList1.clear();
                newText = newText.toLowerCase();
                recyclerView.clearAnimation();
                
                for(String key : c5list.keySet()) {
                    if(c5list.get(key).contains(newText)) 
                    {
                        filteredList1.add(new RecipeObjectModel(key, c5list.get(key).get(0)));
                        CombinedLists(combinelist,filteredList1,filteredList2);
                        flag1=0;
                        checker1=0;
                    }
                    else if(flag1==1)
                    {
                        filteredList1.clear();
                        adapter = new DatabaseAdapter(filteredList1);
                        recyclerView.setAdapter(adapter);
                        flag1=1;
                        checker1=1;
                    }
                }
                
                if((flag1==0 && flag2==0) || (flag1==0 && searchView2.getContext().toString().contains("")) )
                {
                    adapter = new DatabaseAdapter(combinelist);
                    recyclerView.setAdapter(adapter);
                    flag1=1;
                    checker1=0;
                }

                if(newText.equals(""))
                {
                    adapter = new DatabaseAdapter(filteredList2);
                    recyclerView.setAdapter(adapter);
                    checker1=0;
                }

                if(checker2==1)
                {
                    filteredList2.clear();
                    adapter = new DatabaseAdapter(filteredList2);
                    recyclerView.setAdapter(adapter);
                }
                
                if(newText.isEmpty() && filteredList2.isEmpty())
                {
                    adapter = new DatabaseAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
                
                return true;
            }
        });

        
        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            
            @Override
            public boolean onQueryTextSubmit(String query) {return  false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerView.clearAnimation();
                filteredList2.clear();
                newText = newText.toLowerCase();
                
                    for(String key : c5list.keySet()) {
                        if(c5list.get(key).contains(newText)) 
                        {
                            filteredList2.add(new RecipeObjectModel(key, c5list.get(key).get(0)));
                            CombinedLists(combinelist,filteredList1,filteredList2);
                            flag2=0;
                            checker2=0;
                        }
                        
                        else if(flag2==1)
                        {
                            filteredList2.clear();
                            adapter = new DatabaseAdapter(filteredList2);
                            recyclerView.setAdapter(adapter);
                            flag2=1;
                            checker2=1;
                        }
                    }

                if((flag2==0 && flag1==0) || (flag2==0 && searchView1.getContext().toString().contains("")))
                {
                    adapter = new DatabaseAdapter(combinelist);
                    recyclerView.setAdapter(adapter);
                    flag2=1;
                    checker2=0;
                }

                if(newText.equals(""))
                {
                    adapter = new DatabaseAdapter(filteredList1);
                    recyclerView.setAdapter(adapter);
                    checker2=0;
                }

                if(checker1==1)
                {
                    filteredList2.clear();
                    adapter = new DatabaseAdapter(filteredList2);
                    recyclerView.setAdapter(adapter);
                }
                
                if(newText.isEmpty() && filteredList1.isEmpty())
                {
                    adapter = new DatabaseAdapter(data);
                    recyclerView.setAdapter(adapter);
                }
                
                return true;
                    
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    

    public void CombinedLists(ArrayList<RecipeObjectModel> combinelist, ArrayList<RecipeObjectModel> filteredlist1, ArrayList<RecipeObjectModel> filteredlist2)
    {
        combinelist.clear();

        if(filteredlist1.isEmpty())
        {
            combinelist.addAll(filteredlist2);
        }else if(filteredlist2.isEmpty())
        {
            combinelist.addAll(filteredlist1);
        }
        else
        {
           for(RecipeObjectModel e : filteredlist1)
           {
               for(RecipeObjectModel r : filteredlist2)
               {
                   if(e.getIngredient().matches(r.getIngredient()))
                   {
                       combinelist.add(e);
                   }
               }
           }
        }

    }




    public void fetchData()
    {
        db =new DatabaseHelper(this);
        try {

            db.createDataBase();
            db.openDataBase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        c1list = new ArrayList<String[]>();
        c2list=new ArrayList<String[]>();
        c3list=new ArrayList<String[]>();
        c4list=new ArrayList<String[]>();

        c5list=new LinkedHashMap<>();
        foolist=new ArrayList<String>();

        recipes_name_list=new ArrayList<String[]>();
        ingredients_name_list=new ArrayList<String[]>();



        SQLiteDatabase sd = db.getReadableDatabase();

        
        Cursor c1 = sd.rawQuery("select recipe from recipes order by name",null);
        Cursor c2 = sd.rawQuery("select  recipes.name, ingredients.name from recipes left join recipes_ingredients on recipes_ingredients.recipe_id=recipes.id left join ingredients on ingredients.id=recipes_ingredients.ingredient_id order by recipes.name",null);

        c1.moveToFirst();
        c2.moveToFirst();

        String foo=c2.getString(0);
        foolist.add(c1.getString(0));
        int k=0;

        do{
            if(foo.matches(c2.getString(0)))
            {
                foolist.add(c2.getString(1));
            }
            else
            {
                c5list.put(foo, foolist);
                foo=c2.getString(0);
                foolist = new ArrayList<>();
                foolist.add(c1.getString(0));
                foolist.add(c2.getString(1));
                c5list.put(foo, foolist);

            }
            if(c1.moveToNext())
            {

            }
            else c1.moveToLast();
        }while(c2.moveToNext());

        rcp_namelist=new ArrayList<String>();
        c2.moveToFirst();
        String checker = c2.getString(0);
        int xx=0;

        do {
            if(checker.matches(c2.getString(0)) && xx==0)
            {
                rcp_namelist.add(c2.getString(0));
            }
            else if(xx!=0 && !checker.matches(c2.getString(0)))
            {
                rcp_namelist.add(c2.getString(0));
            }
            else
            {

            }
            checker=c2.getString(0);
            xx++;
        }while(c2.moveToNext());

        rcp_executelist= new ArrayList<String>();

        c1.moveToFirst();
        do{
            rcp_executelist.add(c1.getString(0));
        }while(c1.moveToNext());


        for (int i = 0; i < rcp_namelist.size(); i++) {
            data.add(new RecipeObjectModel(rcp_namelist.get(i), rcp_executelist.get(i)));
        }
        adapter = new DatabaseAdapter(data);
        recyclerView.setAdapter(adapter);
    }
}

