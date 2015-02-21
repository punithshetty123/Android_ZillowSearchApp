package com.example.zillowsearch;



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import com.facebook.Response;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.FragmentTransaction;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.Tab;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;


public class ResultActivity extends ActionBarActivity {
		TextView tableColName;
		TextView tableColValue;
		
		Button facebookButtom;
		Button nextButton;
		Button prevButton;
		
		JSONObject obj=null;
		JSONObject resultJSON;
		
		String messageKey=new String();
		String messageValue=new String();
		String address=new String();
		
		TableRow tr;	
		RelativeLayout relativeLayout;
		LinearLayout linearLayout;		
		ImageView imgView;
		LayoutParams lp; 
		int mPosition=0;
	    private Facebook facebook;
	    Response response;
	
		TextSwitcher boldTextSwitcher;
		TextSwitcher textSwitcher;
		ImageSwitcher imageSwitcher;
		
	    ArrayList<String> imagesList=new ArrayList<String>();
	    ArrayList<String> textList=new ArrayList<String>();
	    
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
				
		 try{
		 Intent intent = getIntent();
		   String message = intent.getStringExtra(MainActivity.JSON_STRING);		  
           obj = new JSONObject(message);
		
		   final LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
		 
		   Log.v("width", Integer.toString(layout.getLayoutParams().width));
		   layout.removeAllViews();
		   ActionBar myActionBar = getActionBar(); 
		   myActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		   Tab tab1 = myActionBar.newTab();
		   tab1.setTabListener(new ActionBar.TabListener()
		   { 
		  @Override
		   public void onTabUnselected(Tab tab, FragmentTransaction ft) 
		  { 
		  // TODO Auto-generated method stub
		   } 
		  @Override
		   public void onTabSelected(Tab tab, FragmentTransaction ft) 
		  {
			  try{ 
				  
				  
				  ScrollView sv = new ScrollView(ResultActivity.this);
				  TableLayout ll = new TableLayout(ResultActivity.this);  
				  TableLayout mainTableLayout = new TableLayout(ResultActivity.this); 
				 
				  DisplayMetrics displaymetrics = new DisplayMetrics();
				  getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
				  int width = displaymetrics.widthPixels;
				  int height = displaymetrics.heightPixels;
				  int buttonWidthKey = (3*width)/4;
				  int buttonHeightKey = (3*height)/4;
				  int buttonWidthValue = (width)/4;
				  int buttonWidth = ((width)/2);
				  int sixtyWidth = (int)(0.6*(width));
				  int fourtyWidth = (int)(0.4*(width));
				  
				  lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				  lp.topMargin=50;
				  lp.bottomMargin=50;
				  lp.leftMargin=50;
				  lp.rightMargin=50;
				  ll.setLayoutParams(lp);		 
				  ll.setStretchAllColumns(true);
				  mainTableLayout.setLayoutParams(lp);
				  mainTableLayout.setStretchAllColumns(true);
			  	  resultJSON = obj.getJSONObject("result");
			   
			  	//see more Row starts
				  messageKey="See more details on Zillow:";
				  messageValue=resultJSON.getString("propertytype");
				  tr=new TableRow(ResultActivity.this);
         
				  lp = new LayoutParams(LayoutParams.WRAP_CONTENT, (int)(0.7*height));
				
				  sv.setLayoutParams(lp);
				
				  tableColName = new TextView(ResultActivity.this);
				  tableColName.setText(messageKey);
				  tableColName.setPadding(5, 5,5,5);
				  
				  
				  facebookButtom = new Button(ResultActivity.this);
				//facebookButtom.setPadding(5,5,5,5);
				  facebookButtom.setGravity(Gravity.RIGHT);
				  facebookButtom.setBackgroundResource(R.drawable.fb_share);
				  //calling facebook share on click
				  facebookButtom.setOnClickListener(new View.OnClickListener() {
			             public void onClick(View v) {  
			            	 final HashMap<String,String> hMap=new HashMap<String,String>();
			            	 try {
								address=resultJSON.getString("street")+", "+resultJSON.getString("city")+", "+resultJSON.getString("state")+", "+resultJSON.getString("zipcode");
						
				            	 String url=resultJSON.getString("homedetails");
				            	
				            	 JSONObject chartJSON = obj.getJSONObject("chart");
				            	 JSONObject oneyearJSON = chartJSON.getJSONObject("oneyear");
				            	 
				            	 String description="Last Sold Price:"+resultJSON.getString("lastsoldprice")+", 30 Days Overall Change: "+resultJSON.getString("rentzestimatesign")+resultJSON.getString("thirtydaysrentchangevalue");
				            	 
				            	 hMap.put("picture", oneyearJSON.getString("url"));
				            	 hMap.put("caption", "Property information from Zillow.com");
				            	 hMap.put("link", url);
				            	 hMap.put("description", description);
				            	 hMap.put("name", address);
			            	 } catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
			            	 // Instance of Alert Dialog  
			                 AlertDialog.Builder builder=new Builder(ResultActivity.this);  
			                 builder.setTitle("Post to Facebook")
			                 .setCancelable(false)  
			                 .setPositiveButton("Post Property Details", new DialogInterface.OnClickListener() {  
			             
			                 @Override  
			                 public void onClick(DialogInterface arg0, int arg1) {  
			                	 facebookFeedDisplay(hMap);
			                   // finish(); // Close The Activity  
			                 }  
			              })  
			              .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {  
			              @Override  
			              public void onClick(DialogInterface dialog, int which) {  
			             
			                 dialog.cancel(); // Cancel the Event and back to Activity  
			              }  
			              });  
			             
			                 // Till now, we have builded a Alert Box  
			                 // Show()  
			                 AlertDialog alert =builder.create();  
			                
			                 alert.show();  
			            	 	
			             }
			             });
				  
				  tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
				  tr.setBackgroundResource(R.drawable.tableborder);
			      tr.addView(tableColName) ;
			      tr.addView(facebookButtom) ;
			      ll.addView(tr);
				  
			//see more Row ends  
				  
			//address starts	  
				  address=resultJSON.getString("street")+", "+resultJSON.getString("city")+", "+resultJSON.getString("state")+", "+resultJSON.getString("zipcode");
				  String url=resultJSON.getString("homedetails");
				  tr=new TableRow(ResultActivity.this);
				  TextView tableAddress = new TextView(ResultActivity.this);
				  tableAddress.setText(Html.fromHtml("<a href=\'"+url+"'>"+address+"</a>"));

				  tableAddress.setMovementMethod(LinkMovementMethod.getInstance());
				  tableAddress.setLinksClickable(true);
				  
				  
				  tableAddress.setPadding(5,5,5,5);
				  tableAddress.setWidth((buttonWidth));
			  
			      tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
			      tr.setBackgroundResource(R.drawable.tableborder);
				  tr.addView(tableAddress) ;
				  ll.addView(tr);
		    //address ends
		      
			  //1ST Row starts
				  messageKey="Property Type";
				  messageValue=resultJSON.getString("propertytype");
				  tr= createTable(messageKey,messageValue,"grey");
				  ll.addView(tr);
			//1ST Row ends  
				
			//2nd Row starts
				  messageKey="Year Built";
				  messageValue=resultJSON.getString("yearbuilt");
				  tr= createTable(messageKey,messageValue,"white");
				  ll.addView(tr);
			//2nd Row ends  
				
			//3rd Row starts
				  messageKey="Lot Size";
				  messageValue=resultJSON.getString("lotsize");
				  tr= createTable(messageKey,messageValue,"grey");
				  ll.addView(tr);
			//3rd Row ends 
				
			//4th Row starts
				  messageKey="Finished Area";
				  messageValue=resultJSON.getString("finishedarea");
				  tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//4th Row ends
				
			//5th Row starts
				messageKey="Bathrooms";
				messageValue=resultJSON.getString("bathroomval");
				tr= createTable(messageKey,messageValue,"grey");
				ll.addView(tr);
			//5th Row ends
				
			//6th Row starts
				messageKey="Bedrooms";
				messageValue=resultJSON.getString("bedrooms");
				tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//6th Row ends
				
			//7th Row starts
				messageKey="Tax Assessmet Year";
				messageValue=resultJSON.getString("taxyear");
				tr= createTable(messageKey,messageValue,"grey");
				ll.addView(tr);
			//7th Row ends	
				
			//8th Row starts
				messageKey="Tax Assessment";
				messageValue=resultJSON.getString("taxassesment");
				tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//8th Row ends
					
			//9th Row starts
				messageKey="Last Sold Price";
				messageValue=resultJSON.getString("lastsoldprice");
				tr= createTable(messageKey,messageValue,"grey");
				ll.addView(tr);
			//9th Row ends
					
			//10th Row starts
				messageKey="Last Sold Date";
				messageValue=resultJSON.getString("lastsolddate");
				tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//10th Row ends
					
			//11th Row starts
				messageKey="Zestimate ® Property Estimate as of "+resultJSON.getString("zestimatelastupdatedate");
				messageValue=resultJSON.getString("zestimateamount");
				tr= createTable(messageKey,messageValue,"grey");
				ll.addView(tr);
			//11th Row ends
				
			//12th Row starts
				messageKey="30 Days Overall Change";
				messageValue=resultJSON.getString("valuechange");
				String zestimateSign=resultJSON.getString("zestimatesign");
				
				tr=new TableRow(ResultActivity.this);
				
				tableColName = new TextView(ResultActivity.this);
				tableColName.setText(messageKey);
				tableColName.setPadding(5, 5,5,5);
				tableColName.setWidth(sixtyWidth);          
				
				relativeLayout = new RelativeLayout(ResultActivity.this); 
				 Drawable drawable;
				if(zestimateSign.equals("+")){
					  drawable = getResources().getDrawable(R.drawable.up_g); 
				
				}else{
					  drawable = getResources().getDrawable(R.drawable.down_r); 
				 
				}
			     SpannableString spannableText = new SpannableString(messageValue); 
			      
			       drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()); 
			       ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE); 
			       spannableText.setSpan(span, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE); 
			       
				
				
				tableColValue = new TextView(ResultActivity.this);
				tableColValue.setText(spannableText);
				tableColValue.setPadding(5, 5,5,5);
				tableColValue.setGravity(Gravity.RIGHT);
				tableColValue.setWidth(fourtyWidth);
  
				tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
				tr.setBackgroundResource(R.drawable.tableborder);
				
				//relativeLayout.addView(imgView);
				relativeLayout.addView(tableColValue);
				tr.addView(tableColName) ;
				tr.addView(relativeLayout) ;
				ll.addView(tr);
			//12th Row ends
				
			//13th Row starts
				messageKey="All Time Property Range";
				messageValue=resultJSON.getString("propertyrange");
				tr= createTable(messageKey,messageValue,"grey");
				ll.addView(tr);
			//13th Row ends
				
			//14th Row starts
				messageKey="Rent Zestimate®Valuation as of "+resultJSON.getString("rentzestimatelastupdatedate");
				messageValue=resultJSON.getString("zestimateamount");
				tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//14th Row ends
				
			//15th Row starts
				messageKey="30 days Rent Change";
				messageValue=resultJSON.getString("thirtydaysrentchangevalue");	
				String zestimateRentSign=resultJSON.getString("rentzestimatesign");
				
				tr=new TableRow(ResultActivity.this);
				tableColName = new TextView(ResultActivity.this);
				tableColName.setText(messageKey);
	  			tableColName.setPadding(5, 5,5,5);
	  			tableColName.setWidth(sixtyWidth);
			              
	  			relativeLayout = new RelativeLayout(ResultActivity.this);  
	
	  			imgView=new ImageView(ResultActivity.this);
				  
	  			lp = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.MATCH_PARENT);
	  			lp.height=30;
	  			lp.topMargin=20;
	  			imgView.setLayoutParams(lp);
				   
	  			
				if(zestimateRentSign.equals("+")){
					  drawable = getResources().getDrawable(R.drawable.up_g); 
				
				}else{
					  drawable = getResources().getDrawable(R.drawable.down_r); 
				 
				}
			     spannableText = new SpannableString("  "+messageValue); 
			      
			       drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight()); 
			       span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE); 
			       spannableText.setSpan(span, 0, 1, Spannable.SPAN_USER_SHIFT); 
			       
			    tableColValue = new TextView(ResultActivity.this);
	  			tableColValue.setText(spannableText);
	  			tableColValue.setPadding(5, 5,5,5);
	  			tableColValue.setGravity(Gravity.RIGHT);
	  			tableColValue.setWidth(fourtyWidth);
	  			
	  			tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1)); 
	  			tr.setBackgroundResource(R.drawable.tableborder);
	  			
				relativeLayout.addView(imgView);
				relativeLayout.addView(tableColValue);
				
				tr.addView(tableColName) ;
			    tr.addView(relativeLayout) ;
				
			    ll.addView(tr);
			//15th Row ends
					
			//16th Row starts
				messageKey="All Time Rent Range";
				messageValue=resultJSON.getString("zestimaterange");
				tr= createTable(messageKey,messageValue,"white");
				ll.addView(tr);
			//16th Row ends
			
				TextView disclaimerText1=new TextView(ResultActivity.this);
				TextView disclaimerText2=new TextView(ResultActivity.this);
				TextView disclaimerText3=new TextView(ResultActivity.this);
				disclaimerText1.setGravity(Gravity.CENTER);
				disclaimerText2.setGravity(Gravity.CENTER);
				disclaimerText3.setGravity(Gravity.CENTER);
				
				  linearLayout=new LinearLayout(ResultActivity.this);
				  linearLayout.setOrientation(LinearLayout.VERTICAL);
				  
				  disclaimerText1.setText("\u00A9 Zillow, Inc., 2006-2014.");
				  disclaimerText2.setText(Html.fromHtml("<html>Use is subject to <a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a></html>"));
				  disclaimerText3.setText(Html.fromHtml("<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate?</a>"));

				  disclaimerText1.setMovementMethod(LinkMovementMethod.getInstance());
				  disclaimerText2.setMovementMethod(LinkMovementMethod.getInstance());
				  disclaimerText3.setMovementMethod(LinkMovementMethod.getInstance());
				  
				  disclaimerText1.setLinksClickable(true);
				  disclaimerText2.setLinksClickable(true);
				  disclaimerText3.setLinksClickable(true);
				  
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText1);
				  linearLayout.addView(tr);
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText2);
				  linearLayout.addView(tr);
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText3);
				  linearLayout.addView(tr);
				  sv.addView(ll);
			// tr=new TableRow(ResultActivity.this);
			// tr.addView(sv);
			 mainTableLayout.addView(sv);
			 mainTableLayout.addView(linearLayout);
			  layout.removeAllViews();
			  
			  
			  layout.addView(mainTableLayout);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	   } 
		  @Override 
		  public void onTabReselected(Tab tab, FragmentTransaction ft)
		   { 
		  // TODO Auto-generated method stub
		   } });
			 
		   tab1.setText("Basic Info");
		   myActionBar.addTab(tab1);		   
		   
		   Tab tab2 = myActionBar.newTab();
		   tab2.setTabListener(new ActionBar.TabListener()
		   { 
		  @Override
		   public void onTabUnselected(Tab tab, FragmentTransaction ft) 
		  { 
		  // TODO Auto-generated method stub
		   } 
		  @Override
		   public void onTabSelected(Tab tab, FragmentTransaction ft) 
		  { 
			  try{
				  JSONObject resultJSON;
				  JSONObject chartJSON;
				  JSONObject oneyearJSON;
				  JSONObject fiveyearJSON;
				  JSONObject tenyearJSON;
				  resultJSON = obj.getJSONObject("result");
				  chartJSON = obj.getJSONObject("chart");
				  oneyearJSON = chartJSON.getJSONObject("oneyear");
				  fiveyearJSON = chartJSON.getJSONObject("fiveyear");
				  tenyearJSON = chartJSON.getJSONObject("tenyear");
				 		  
				  imagesList.add(oneyearJSON.getString("url"));
				  imagesList.add(fiveyearJSON.getString("url"));
				  imagesList.add(tenyearJSON.getString("url"));
				  
				  textList.add("Historical Zestimate for the past 1 year");
				  textList.add("Historical Zestimate for the past 5 year");
				  textList.add("Historical Zestimate for the past 10 year");
				  
				  address=resultJSON.getString("street")+", "+resultJSON.getString("city")+", "+resultJSON.getString("state")+", "+resultJSON.getString("zipcode");
	
				 
				

			        // set the animation type of textSwitcher
			       
				  linearLayout =new LinearLayout(ResultActivity.this) ;
				  linearLayout.setOrientation(LinearLayout.VERTICAL);
				 // lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				 // linearLayout.setLayoutParams(lp);
				  
				  LinearLayout buttonLinearLayout =new LinearLayout(ResultActivity.this) ;
				
				  buttonLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
				  buttonLinearLayout.setGravity(Gravity.CENTER);
			  
				  imageSwitcher=new ImageSwitcher(ResultActivity.this);
				  nextButton=new Button(ResultActivity.this);
				  prevButton=new Button(ResultActivity.this);
				  nextButton.setText("Next");
				  prevButton.setText("Previous");

				  boldTextSwitcher=new TextSwitcher(ResultActivity.this);
				  boldTextSwitcher.setFactory(new ViewFactory() {
				  @Override
				  public View makeView() {
					LinearLayout ll=new LinearLayout(ResultActivity.this);
					TextView textView = new TextView(ResultActivity.this);
					textView.setGravity(Gravity.CENTER_HORIZONTAL);
					textView.setTypeface(null, Typeface.BOLD);
					textView.setTextSize(18);
					return textView;
				  	}
				  });
			
				  textSwitcher=new TextSwitcher(ResultActivity.this);
				  textSwitcher.setFactory(new ViewFactory() {
				 @Override
				 public View makeView() {
					TextView textView = new TextView(ResultActivity.this);
					textView.setGravity(Gravity.CENTER_HORIZONTAL);
					return textView;
				 	}
				  });
				 imageSwitcher.setFactory(new ViewFactory() {
				 @Override
				 public View makeView() { 
					imgView = new ImageView(ResultActivity.this);
					//imgView.setFitToScreen(true);
					 DisplayMetrics displaymetrics = new DisplayMetrics();
					  getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
					  int width = displaymetrics.widthPixels;
					  int height = displaymetrics.heightPixels; 
					  int measurementHeight=(width>height)?height:width;
					  Log.v("widthmod", Integer.toString(measurementHeight));
					  Log.v("width", Integer.toString(width));
					 imgView.setScaleType(ScaleType.FIT_XY);
					 imgView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
					imgView.getLayoutParams().height = (int)(0.75*measurementHeight);
					imgView.setPadding(0, 40, 0, 40);
					return imgView;
					}
				 });
				
				 boldTextSwitcher.setText(textList.get(0));
				 textSwitcher.setText(address);
				 new chartParse().execute(imagesList.get(0));
				 nextButton.setOnClickListener(new View.OnClickListener() {
					 
					 public void onClick(View v) {
			            mPosition = (mPosition + 1) % imagesList.size();	 
			            boldTextSwitcher.setText(textList.get(mPosition));
						textSwitcher.setText(address);
						new chartParse().execute(imagesList.get(mPosition));
						
					 	}
				 	});
			
				 prevButton.setOnClickListener(new View.OnClickListener() {
					 
					 public void onClick(View v) {
			            mPosition = ((mPosition +imagesList.size()-1) % imagesList.size());
			            boldTextSwitcher.setText(textList.get(mPosition));
						textSwitcher.setText(address);
						new chartParse().execute(imagesList.get(mPosition));
						
		                 }
			        });
				  LinearLayout linearLayout1=new LinearLayout(ResultActivity.this);
				  linearLayout1.setOrientation(LinearLayout.VERTICAL);
				  lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
				  lp.gravity=Gravity.CENTER_HORIZONTAL;
				  linearLayout1.setLayoutParams(lp);
				 
				  TextView disclaimerText1=new TextView(ResultActivity.this);
				  TextView disclaimerText2=new TextView(ResultActivity.this);
				  TextView disclaimerText3=new TextView(ResultActivity.this);
				  disclaimerText1.setGravity(Gravity.CENTER);
				  disclaimerText2.setGravity(Gravity.CENTER);
				  disclaimerText3.setGravity(Gravity.CENTER);
				  
				  disclaimerText1.setText("\u00A9 Zillow, Inc., 2006-2014.");
				  disclaimerText2.setText(Html.fromHtml("<html>Use is subject to <a href=\"http://www.zillow.com/corp/Terms.htm\">Terms of Use</a></html>"));
				  disclaimerText3.setText(Html.fromHtml("<a href=\"http://www.zillow.com/zestimate/\">What's a Zestimate?</a>"));

				  disclaimerText1.setMovementMethod(LinkMovementMethod.getInstance());
				  disclaimerText2.setMovementMethod(LinkMovementMethod.getInstance());
				  disclaimerText3.setMovementMethod(LinkMovementMethod.getInstance());
			
				linearLayout.addView(boldTextSwitcher);
				linearLayout.addView(textSwitcher);
				linearLayout.addView(imageSwitcher);
				
				//buttonLinearLayout.addView(prevButton);
				//buttonLinearLayout.addView(nextButton);
				
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText1);
				  linearLayout1.addView(tr);
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText2);
				  linearLayout1.addView(tr);
				  tr=new TableRow(ResultActivity.this);
				  tr.addView(disclaimerText3);
				  linearLayout1.addView(tr);
				
				//linearLayout.addView(buttonLinearLayout);
				//linearLayout.addView(linearLayout1);
				
				
				layout.removeAllViews();

				layout.addView(linearLayout);
				buttonLinearLayout.addView(prevButton);
				buttonLinearLayout.addView(nextButton);
				layout.addView(buttonLinearLayout);
				layout.addView(linearLayout1);
			  	}catch(Exception e){
				  e.printStackTrace();
			  	}
		   } 
		  @Override 
		  public void onTabReselected(Tab tab, FragmentTransaction ft)
		   { 
		  // TODO Auto-generated method stub
		   } });
		   tab2.setText("histogram");		  
		   myActionBar.addTab(tab2);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}		   
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public TableRow createTable(String key,String value,String color){	
		TableRow tr=new TableRow(ResultActivity.this);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int width = displaymetrics.widthPixels;
		int buttonWidth = width/2;
		int buttonWidthKey = (3*width)/4;
		int buttonWidthValue = (width)/4;
		int sixtyWidth = (int)(0.6*(width));
		int fourtyWidth = (int)(0.4*(width));
		tableColName = new TextView(ResultActivity.this);
		tableColName.setText(key);
		tableColName.setPadding(5, 5,5,5);
		tableColName.setWidth(sixtyWidth);  
		tableColValue = new TextView(ResultActivity.this);
		tableColValue.setText(value);
		tableColValue.setPadding(5, 5,5,5);
		tableColValue.setGravity(Gravity.RIGHT);
		tableColValue.setWidth(fourtyWidth);
		tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
		if(color.equals("white")){
		  tr.setBackgroundResource(R.drawable.tableborder);
		}else if(color.equals("grey")){
		  tr.setBackgroundResource(R.drawable.tablebordergrey);
		}
	      tr.addView(tableColName) ;
	      tr.addView(tableColValue) ;
	      return tr;
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			return rootView;
		}
	}

	 private class chartParse extends AsyncTask<String, Bitmap, Bitmap> {		 
		@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			InputStream is=null;
			Log.v("inside background execute","success");
			 DefaultHttpClient client = new DefaultHttpClient();
	            HttpPost post = new HttpPost(params[0]);
	            HttpResponse response = null;
				try {
					response = client.execute(post);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            HttpEntity entity = response.getEntity();
	            try {
					is = entity.getContent();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 Bitmap bitmap = BitmapFactory.decodeStream(is);		      
			return bitmap;
		}
		 @Override
	      protected void onPostExecute(Bitmap bitmap) {
			 try{
			 Drawable drawable =new BitmapDrawable(bitmap);
				
				imageSwitcher.setImageDrawable(drawable);
				Log.v("inside post execute","success");
			 }catch(Exception e){
				 Toast.makeText(ResultActivity.this, "Internet Issue: Images not retrieved", Toast.LENGTH_LONG).show();
			 }
		 }
		 }
	
	 public void facebookFeedDisplay(HashMap<String, String> hashMap){
		 facebook=new Facebook("384714645029882");
		 Bundle params = new Bundle();
		 params.putString("name", hashMap.get("name"));
		 params.putString("caption", hashMap.get("caption"));
		 params.putString("description", hashMap.get("description"));
		 params.putString("link", hashMap.get("link"));
		 params.putString("picture", hashMap.get("picture"));
		 Log.v("hashmap", hashMap.toString());
		
		
	     facebook.dialog(ResultActivity.this,"feed",params, new DialogListener() {
				
				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					Toast.makeText(ResultActivity.this, "Facebook Error", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
				}
				
				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub

					String postMessage="Posted Story, ID:"+values.getString("post_id");
		 if(values.getString("post_id")!=null){
					Toast.makeText(ResultActivity.this, postMessage, Toast.LENGTH_LONG).show();
					}
		 else{
		 Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
			 }
		 
				}
				
				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
					
				}
			});

	 }
}