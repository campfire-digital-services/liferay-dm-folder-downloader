/**
 * Copyright (C) 2013-2015 Permeance Technologies
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see <http://www.gnu.org/licenses/>.
 */
package au.com.permeance.liferay.portlet.util;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;


/**
 * String Util Helper.
 * 
 * @author Tim Telcik <tim.telcik@permeance.com.au>
 * 
 * @see StringUtil
 */
public class StringUtilHelper {
	
	public static String addDelimItems( String str, String delimItems ) {
		return addDelimItems( str, delimItems, StringPool.COMMA );
	}
	
	
	public static String addDelimItems( String str, String delimItems, String delimiter ) {
		return addDelimItems( str, delimItems, delimiter, false );
	}
	
	
	public static String addDelimItems( String str, String delimItems, String delimiter, boolean allowDuplicates ) {
		
		String result = str;
		
		if (delimItems != null) {
			String[] items = StringUtil.split( delimItems, delimiter );
			result = addArrayItems( str, items, delimiter, allowDuplicates );
		}
		
		return result;
	}
	
	
	public static String addArrayItems( String str, String[] items ) {
		return addArrayItems( str, items, StringPool.COMMA );
	}
	
	
	public static String addArrayItems( String str, String[] items, String delimiter ) {
		return addArrayItems( str, items, delimiter, false );
	}
	
	
	public static String addArrayItems( String str, String[] items, String delimiter, boolean allowDuplicates ) {
		
		String result = str;
		
		if (items != null) {
			for (String item : items) {
				result = StringUtil.add( result, item, delimiter, allowDuplicates );
			}
		}
		
		return result;
	}

	
	public static String removeDelimItems( String str, String delimItems ) {
		return removeDelimItems( str, delimItems, StringPool.COMMA );
	}
	
	
	public static String removeDelimItems( String str, String delimItems, String delimiter ) {
		
		String result = str;
		
		if (delimItems != null) {
			String[] items = StringUtil.split( delimItems, delimiter );
			result = removeArrayItems( str, items, delimiter );
		}
		
		return result;
	}
	
	
	public static String removeArrayItems( String str, String[] items ) {
		return removeArrayItems( str, items, StringPool.COMMA );
	}
	
	
	public static String removeArrayItems( String str, String[] items, String delimiter ) {
		
		String result = str;
		
		if (items != null) {
			for (String item : items) {
				result = StringUtil.removeFromList( result, item, delimiter );
			}
		}
		
		return result;
	}
	
}
