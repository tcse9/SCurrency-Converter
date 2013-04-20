package net.sporix.scurrencyconverter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class MobileArrayAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] values;
	
	private int[] values2 = {R.drawable.aed, R.drawable.ang, R.drawable.ars, R.drawable.aud, R.drawable.bdt, R.drawable.bgn, R.drawable.bhd, R.drawable.bnd, R.drawable.bob, R.drawable.brl,  R.drawable.bwp, R.drawable.cad,
			R.drawable.chf, R.drawable.clp, R.drawable.cny, R.drawable.cop, R.drawable.crc, R.drawable.czk, R.drawable.dkk, R.drawable.dop, R.drawable.dzd, R.drawable.eek, R.drawable.egp,
			R.drawable.eur, R.drawable.fjd, R.drawable.gbp, R.drawable.hkd,
			R.drawable.hnl, R.drawable.hrk, R.drawable.huf, R.drawable.idr, R.drawable.ils, R.drawable.inr, R.drawable.jmd, R.drawable.jod, R.drawable.jpy,
			R.drawable.kes, R.drawable.krw, R.drawable.kwd, R.drawable.kyd, R.drawable.kzt, R.drawable.lbp, R.drawable.lkr, R.drawable.ltl,
			R.drawable.lvl, R.drawable.mad, R.drawable.mdl, R.drawable.mkd, R.drawable.mur,R.drawable.mvr, R.drawable.mxn, R.drawable.myr,
			R.drawable.nad, R.drawable.ngn, R.drawable.nio, R.drawable.nok, R.drawable.npr, R.drawable.nzd, R.drawable.omr, R.drawable.pen, R.drawable.pgk, R.drawable.php, R.drawable.pkr, R.drawable.pln, R.drawable.pyg,
			R.drawable.qar, R.drawable.ron, R.drawable.rsd, R.drawable.rub, R.drawable.sar, R.drawable.scr, R.drawable.sek, R.drawable.sgd, R.drawable.skk, R.drawable.sll, R.drawable.svc, R.drawable.thb, R.drawable.tnd, R.drawable.try_, R.drawable.ttd, R.drawable.twd, R.drawable.tzs,
			R.drawable.uah, R.drawable.ugx, R.drawable.usd, R.drawable.uyu, R.drawable.uzs, R.drawable.vef, R.drawable.vnd, R.drawable.xof, R.drawable.yer,
			R.drawable.zar, R.drawable.zmk};
	
	private String [] arrayCurrency = {"AED", "ANG", "ARS", "AUD", "BDT", "BGN", "BHD", "BND", "BOB", "BRL",  "BWP", "CAD",
			"CHF", "CLP", "CNY", "COP", "CRC", "CZK", "DKK", "DOP", "DZD", "EEK", "EGP",
			"EUR", "FJD", "GBP", "HKD",
			"HNL", "HRK", "HUF", "IDR", "ILS", "INR", "JMD", "JOD", "JPY",
			"KES", "KRW", "KWD", "KYD", "KZT", "LBP", "LKR", "LTL",
			"LVL", "MAD", "MDL", "MKD", "MUR", "MVR", "MXN", "MYR",
			"NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG",
			"QAR", "RON", "RSD", "RUB", "SAR", "SCR", "SEK", "SGD", "SKK", "SLL", "SVC", "THB", "TND", "TRY", "TTD", "TWD", "TZS",
			"UAH", "UGX", "USD", "UYU", "UZS", "VEF", "VND", "XOF", "YER",
			"ZAR", "ZMK"};
	
 
	public MobileArrayAdapter(Context context, String[] values) {
		super(context, R.layout.spinner_item, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.spinner_item, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.label);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.logo);
		textView.setText(values[position]);
 
		// Change icon based on name
		String s = values[position];
 
		//System.out.println(s);
		
		Log.v("mobile", "name: "+s);
 
		
		for(int i = 0 ;i <values.length;i++)
		{
		
			if (s.equals(arrayCurrency[i])) {
				imageView.setImageResource(values2[i]);
			}
		/*} else if (s.equals(values2[1])) {
			imageView.setImageResource(R.drawable.ang);
		} else if (s.equals(values2[2])) {
			imageView.setImageResource(R.drawable.ars);
		} else if (s.equals(values2[3])){
			imageView.setImageResource(R.drawable.ars);
		}
		else if (s.equals(values2[4])){
			imageView.setImageResource(R.drawable.aud);
		}
		else if (s.equals(values2[5])){
			imageView.setImageResource(R.drawable.aud);
		}*/
		
		}
 
		return rowView;
	}
}
