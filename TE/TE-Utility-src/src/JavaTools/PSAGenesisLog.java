
//COPYRIGHT PSA Peugeot Citroen 2010
/**********************************************************************************************************
 *
 * FILE NAME	: PSAGenesisLog.java
 *
 * SOCIETE        : PSA
 * PROJET         : 151AP - Gestion d'Accès à la MNU Vues Fournisseurs - UI
 * VERSION        : V1.0
 * DATE           : 14/10/2010
 *
 * AUTEUR         : GEOMETRIC GLOBAL
 *
 * DESCRIPTION    : Class for generating the query files used in 3DCOM and VPM
 *					
 * MODIFICATIONS  :
 *         DATE   :
 *         AUTEUR :
 *         OBJET  :
 *
**********************************************************************************************************/
package com.psa.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class PSAGenesisLog {
	
	/**
	 * Append log for Selction of Filter Format.
	 * @param strNomMod			String to be add.
	 * @return	Zero 	: Success
	 * 			NonZero	: Failure
	 */
	public static int genesis(String strNomMod)
	{
		int ier = 0;
		String strHost = System.getenv("HOST");
		String strLogName = System.getenv("LOGNAME");
		String strEtat = System.getenv("ETAT");
		String strLogiciel = System.getenv("LOGICIEL");

		System.out.println("HOST :" + strHost + ", LOGNAME : " + strLogName + ", ETAT : " + strEtat + ", LOGICIEL : " + strLogiciel);
		if ((strHost == null) || (strLogName == null) || (strEtat == null) || (strLogiciel == null) )
		{
			System.out.println("Environment variables not present.");
			ier = -2;
			return ier;
		}

		//As directory $LOCAL/$LOGNAME validity and creation is not required so removed code.
		String strDirName = System.getenv("LOCAL") +"/" + System.getenv("LOGNAME");
		File nomRepertoireDir =  new File(strDirName);
		if (!nomRepertoireDir.exists()) {
			boolean bStatus = nomRepertoireDir.mkdir();
			if (bStatus == false) {
				System.out.println("Unable to create directory $LOCAL/$LOGNAME");
				ier = -2;
				return ier;
			}
		}

		Calendar calendar =  Calendar.getInstance();
		String nomFichier_s = String.format("%s/genesisplus.%04d-%03d", strDirName, calendar.get(Calendar.YEAR), calendar.get(Calendar.DAY_OF_YEAR));
		System.out.println("Genesis file name : " + nomFichier_s);
		File genesisFile =  new File(nomFichier_s);
		FileOutputStream fileOutputStream;
		try {
				String strStartDate, strEndDate;
				fileOutputStream = new FileOutputStream(genesisFile, true);
				strStartDate = String.format("%04d.%03d.%02d:%02d", calendar.get(Calendar.YEAR),
											calendar.get(Calendar.DAY_OF_YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

				calendar.add(Calendar.MINUTE, 3); /* on impose une durée d'utilisation de 3 minutes */

				strEndDate  = String.format("%04d.%03d.%02d:%02d", calendar.get(Calendar.YEAR),
											calendar.get(Calendar.DAY_OF_YEAR), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));


				String strData = String.format("%s;%s;%s;%s;;;;%s;%s;%s\n", strHost, strLogName, strEtat, strLogiciel, strStartDate, strEndDate, strNomMod);

				fileOutputStream.write(strData.getBytes());

				fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("Error while writing to  genesis file.");
			ier = -3;
		}

		return ier;

	}
}
