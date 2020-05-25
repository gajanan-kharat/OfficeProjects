package com.inetpsa.pv2.batch;

public class PictoConstants {

    /** Opposite of {@link #FAILURE}. */
    public static final boolean SUCCESS = true;
    /** Opposite of {@link #SUCCESS}. */
    public static final boolean FAILURE = false;

    public static final String FILE_SUFFIX = ".picxml";
    public static final String FILE_PICXML = "picxml";
    public static final String FILE_AI = ".ai";
    public static final String FILE_JPG = ".jpg";
    public static final String FILE_PNG = ".png";
    public static final String FILE_IGS = ".igs";
    public static final String PICTO_NAME_SEPARATOR = "_";
 public static final String ZIP_FILE = ".zip";
    public static final String AI_PUBLIC_FILE = "AI_Single_Layer";
  public static final String FILE_SETTER = "file";
    /* File extenstions */
    public static final String AI_IMG = "ai";
    public static final String JPG_IMG = "jpg";
    public static final String PNG_IMG = "png";
    public static final String IGS_IMG = "igs";

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /* get Pictos for logged in Admin */
    public static final String GET_PICTOS_BY_USRID_URL = "/thickclients/getAllPictoClient";
    /* get all Pictos for logged in Admin */
    public static final String GET_ALLPICTOS_BY_USRID_URL = "/thickclients/getAllPictoList";
    /* get working admin list */
    public static final String GET_WORKING_ADMIN_LIST_URL = "/thickclients/getWorkAdminList";
    /* get pictoClient */
    public static final String GET_PICTOCLIENT_BY_PICTOID_URL = "/thickclients/getPictoClient";
    /* reset download flag */
    public static final String PICTO_RESET_DWNLOAD_FLAG_URL = "/thickclients/resetFlag";
    /* delete picto */
    public static final String PICTO_DELETE_URL = "/thickclients/delete";
    /* update AIFile last updated info */
    public static final String PICTO_LASTUPDATE_URL = "/thickclients/updateAIFile";
    /* Thick client launch flag */
    public static final String PICTO_SET_LAUNCH_FLAG_URL = "/thickclients/launchFlag";
    /* rest user authentication */
    public static final String PICTO_LDAP_AUTHENTICATION_URL = "/thickclients/loginAuthentication";
    /* image download URL */
    public static final String PICTO_IMG_DWNLOAD_URL = "/thickclients/download?name=";
    /* Image upload URL */
    public static final String PICTO_IMG_UPLOAD_URL = "/thickclients/uploadFile?name=";
    /* to delete zip file created at server location */
    public static final String DLT_SRVR_ZIP_FILE_URL = "/thickclients/deleteZipFile?name=";
    /* installation directory to check thick client is installed or not */
    public static final String THICK_CLIENT_CONF_DIR = "C:\\palette\\apl\\pictothequev2";
    /* Picto download for Web URL */
    public static final String PICTO_ZIP_TO_DOWNLOAD = "/picto/DownloadImages";
 public static final String USR_FIRST_NAME = "firstName";
    public static final String USR_ID = "id";
    /*
     * for executing batch file for conversion
     */
    public static final String RUN_CONVERSION_BATCH_COMMAND = "cmd /c start /wait ";
    public static final String RUN_BATCH_COMMAND = "cmd /c start ";
    
    /* Language */
    public static final String FRENCH = "Fr";
    public static final String ENGLISH = "En";
}
