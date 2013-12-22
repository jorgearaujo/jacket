package com.araujo.jacket.message;


public class Messages {
	public static String DUPLICATE_URI = "WARNING: Jacket found a duplicate mapping with uri '{%}'. Update the existing one or the first added will be had in count.";
	public static String DUPLICATE_EXCEPTION = "WARNING: Jacket found a duplicate mapping with exception '{%}'. Update the existing one or the first added will be had in count.";
	public static String DUPLICATE_ACTION = "WARNING: Jacket found a duplicate mapping with action '{%}'. Update the existing one or the first added will be had in count.";
	
	public static String INVALID_MAPPING_VIEW = "ERROR: Error adding action mapping because uri and page must be specified.";
	
	public static String INVALID_MAPPING_ACTION_NO_ACTION = "ERROR: Error adding action mapping because action is not specified.";
	public static String INVALID_MAPPING_ACTION_PAGE_AND_URI = "ERROR: Error adding action mapping with action '{%}' because page and uri are mapped, and only one can be mapped.";
	public static String INVALID_MAPPING_ACTION_NO_PAGE_AND_NO_URI = "ERROR: Error adding action mapping with action '{%}' because page and uri aren't mapped, and one of them must be mapped.";
	
	public static String INVALID_MAPPING_EXCEPTION_NO_EXCEPTION = "ERROR: Error adding exception mapping because exception class is not specified.";
	public static String INVALID_MAPPING_EXCEPTION_PAGE_AND_URI = "ERROR: Error adding exception mapping with exception '{%}' because page and uri are mapped, and only one can be mapped.";
	public static String INVALID_MAPPING_EXCEPTION_NO_PAGE_AND_NO_URI = "ERROR: Error adding exception mapping with exception '{%}' because page and uri aren't mapped, and one of them must be mapped.";
	
	public static String JACKET_CONFIG_CANNOT_INSTANTIATE = "ERROR: Class passed as Jacket Configuration ({%}) cannot be instantiated.";
	public static String JACKET_CONFIG_CANNOT_ACCESS = "ERROR: Class passed as Jacket Configuration ({%}) cannot be accesed. Make sure it has public access.";
	
	public static String LOCALE_PROVIER_CANNOT_INSTANTIATE = "ERROR: Class passed as locale provider ({%}) cannot be instantiated.";
	public static String LOCALE_PROVIDER_CANNOT_ACCESS = "ERROR: Class passed as locale provider ({%}) cannot be accesed. Make sure it has public access.";
	
	public static String XML_CONFIG_FILE_NOT_EXIST = "ERROR: XML file used to configure jacket does not exist ({%}).";
	public static String XML_CONFIG_FILE_CANNOT_READ = "ERROR: XML file used to configure jacket cannot be read.";
	public static String CLASS_CONFIG_FILE_NOT_EXIST = "ERROR: Class used to configure jacket does not exist ({%}).";
	
}
