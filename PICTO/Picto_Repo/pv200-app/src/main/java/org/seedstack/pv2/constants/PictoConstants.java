package org.seedstack.pv2.constants;

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

    /* Information Tag */
    public static final String TYPE_NOTHING = "";
    public static final String TYPE_VALID = "none";
    public static final String TYPE_INFO = "info";
    public static final String TYPE_WARNING = "warning";

    public static final String INFO_VALID = "Valide";
    public static final String INFO_READ = "Information à lire";
    public static final String INFO_WARN = "Précautions d'usage à lire";

    public static final String PIC_XML_STATUS = "on";
    public static final String ZIP_FILE = ".zip";
    public static final String FILE_SETTER = "file";
    public static final String PDF_FILE = ".pdf";

    /* validation Level */
    public static final String LEVEL_VALID = "validate";
    public static final String LEVEL_INPROGRESS = "work in progress";

    /* Back up file in directory */
    public static final String BACK_FILE = "back";

    public static final String AI_PUBLIC_FILE = "AI_Single_Layer";

    /* File extensions */
    public static final String AI_IMG = "ai";
    public static final String JPG_IMG = "jpg";
    public static final String PNG_IMG = "png";
    public static final String IGS_IMG = "igs";
    public static final String AIW_IMG = "aiw";

    public static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /* get Pictos for logged in Admin */
    public static final String GET_PICTOS_BY_USRID_URL = "/thickclients/id";
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
    /* Picto Type */
    public static final String FRONTAGE = "Frontage";
    /* image type to search all images with png ext */
    public static final String PNG_IMAGES = "*.png";
    /* Downloads folder */
    public static final String DOWNLOAD_ZIP_NAME = "Picto_Downloads";

    /* Image from server loaction */
    public static final String GET_IMAGE = "/picto/getImage?name=";

    /* Language constants */
    public static final String FR_LANG = "fr";
    public static final String EN_LANG = "en";

    /* Constants to get table column values from rest response */
    /* Added for Single /Multiple PDF Export Report */
    public static final String AUTHOR_TITLE = "PSA";
    public static final String REFERENCE_TITLE = "Picto: ";
    public static final String ADMIN = "Admin";
    public static final String TYPE = "TYPE";
    public static final String NOM_CHARTE = "REF. CHARTE";
    public static final String SUR_CATEGORIES = "Sur-Catégories : ";
    public static final String CATEGORIES = "CATÉGORIES";
    public static final String SOUS_CATEGORIES = "Sous-Categories : ";
    public static final String RHN = "RHN";
    public static final String ECLAIREE = "Commande :";
    public static final String TYPE_DEMOINS = "Temoins";
    public static final String ACTIVATION_FR = "Témoins d’activation : ";
    public static final String ALERTE_FR = "Témoins d’alerte :";
    public static final String DEFIANCE_FR = "Témoins de défaillance :";
    public static final String CASD_USAGE = "FONCTION";
    public static final String INFORMATION = "Information";
    public static final String MOTS_CLES = "MOTS CLÉS";
    public static final String TIRTEE_SECONDAIRE = "Tirette, info, secondaire";
    public static final String PRIVE = "Privé";
    public static final String COMMANDANDTELLFR = "COMMANDE ET TEMOIN";
    public static final String RHNINFOFR = "INFORMATIONS RHN";
    public static final String PDFINFOFR = "Info";
    public static final String PDFWARNFR = "Attention";
    public static final String PDFVALIDFR = "Valider";
    public static final String PDFWIPFR = "Travail en cours";

    /* Added for Single/Multiple English PDF Export Report */
    public static final String NOM_CHARTE_ENG = "Name Charter : ";
    public static final String SUR_CATEGORIES_ENG = "Super-Categories : ";
    public static final String CATEGORIES_ENG = "CATEGORIES";
    public static final String SOUS_CATEGORIES_ENG = "Sub-Categories : ";
    public static final String REGLEMENT_ENG = "SETTLEMENT";
    public static final String ECLAIREE_ENG = "Command :";
    public static final String TYPE_DEMOINS_ENG = "Telltales";
    public static final String ALERTE_ENG = "Alerte :";
    public static final String CASD_USAGE_ENG = "FUNCTION";
    public static final String INFORMATION_ENG = "Information";
    public static final String MOTS_CLES_ENG = "KEYWORDS";
    public static final String PRIVE_ENG = "Private";
    public static final String DEFIANCE_ENG = "Failure :";
    public static final String COMMANDANDTELLEN = "COMMAND AND TELLTALE";
    public static final String RHNINFOEN = "RHN INFORMATIONS";
    public static final String PDFINFOEN = "Info";
    public static final String PDFWARNEN = "Warning";
    public static final String PDFVALIDEN = "Validate";
    public static final String PDFWIPEN = "Work in progress";
    public static final String ACTIVATION_EN = "Activation telltale :";
    public static final String ALERTE_EN = "Alert telltale :";
    public static final String DEFIANCE_EN = "Default telltale :";
    public static final String CALIBRI = "Calibri";
    public static final int PDF_FONTSIZE = 12;
    public static final String EMPTY_STRING = "";

    public static final String REGLEMENT = "RÈGLEMENT";
    public static final String MESSAGE_SUCCESS = "SUCCESS";
    public static final String MESSAGE_FAILURE = "FAILURE";
    public static final String FILE_SEPRATOR = "/";
    public static final String USR_FIRST_NAME = "firstName";
    public static final String USR_ID = "id";
    public static final String PICTO_MED = "Med.jpg";
    public static final String SINGLE_PDF_REST_CALL = "/pictoFamily/getSinglePdf";
    public static final String MULTI_PDF_REST_CALL = "/pictoFamily/getMultiplePdf";
    public static final String MULTI_PDF_DOWNLOAD = "/pictoFamily/GetMultiplePDF?name=";
    public static final String SINGLE_PDF_DOWNLOAD = "/pictoFamily/GetSinglePDF";

    public static final String PDF_PAGE_NUMBER = "1-";
    public static final String GET_SINGLE_PDF = "/pictoFamily/getMultiPdf?name=";
    /* End Added for Single/Multiple PDF Export */
    public static final String INFO_IN_PROGRESS = "'warning'";
    public static final String INFO_TO_BE_READ = "'info'";
    public static final String VALIDATION_LEVEL_INPROGRESS = "'work in progress'";
    public static final String VALIDATION_LEVEL_VALIDATE = "'validate'";

    /* * Visibility */
    public static final String VISIBLE = "Visible";
    public static final String INVISIBLE = "Invisible";
    public static final String NOTAVAILABLE = "Not Available";

    public static final String OR_INSERTION = " OR ";
    public static final String AND_SEARCH = "+";
    public static final String OR_SEPRATOR = "|";
    public static final int AND_SEARCH_SUBSTRING = 1;

    /*
     * 
     * Added during code review to remove hardcoded values
     */
    public static final String RED = "red";
    public static final String YELLOW = "yellow";
    public static final String REFERENCE_NUM = "Réf";
    public static final String REFERENCE_NUMEN = "Ref";
    public static final String CHARTE_NUM = "Charte";
    public static final String CHARTE_NUMEN = "Charter";

    public static final String DATE_FORMAT = "yyyy-mm-dd";
    public static final String UPDATED_DATE = "Date";
    public static final String AFTER = "After";
    public static final String BEFORE = "Before";

    public static final String MAX_REFERENCE_NUMBER_QUERY = "select Max(CAST(REF_NUM AS UNSIGNED)) from PV2QTPFM";
    public static final String AND_SEPRATOR = ".+";

    /* Batch Migration constants */
    public static final String PDF_EXT = "_pdf.";
    public static final String PNG_EXT = "_SMEG7pouces.png";
    public static final String LITTLE_EXT = "_Little";
    public static final String TINY_EXT = "_Tiny";
    public static final String MID_EXT = "_Med";
    public static final String AIP_EXT = "_AIP";

    public static final String SPECIFICDRAW = "Specific_Drawing";

    public static final String Medium = "Med";
    public static final String Little = "Little";
    public static final String Tiny = "Tiny";

    public static final String TYPE_ICON = "Iconothèque";

    private PictoConstants() {
        // Do nothing
    }

}
