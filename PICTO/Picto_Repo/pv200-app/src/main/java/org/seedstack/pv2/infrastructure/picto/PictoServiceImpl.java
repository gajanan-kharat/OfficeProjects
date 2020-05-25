package org.seedstack.pv2.infrastructure.picto;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.seedstack.pv2.application.PictoService;
import org.seedstack.pv2.constants.PictoConstants;
import org.seedstack.pv2.domain.category.Category;
import org.seedstack.pv2.domain.category.CategoryRepository;
import org.seedstack.pv2.domain.color.Color;
import org.seedstack.pv2.domain.color.ColorRepository;
import org.seedstack.pv2.domain.image.ImageType;
import org.seedstack.pv2.domain.image.ImageTypeRepository;
import org.seedstack.pv2.domain.picto.Picto;
import org.seedstack.pv2.domain.picto.PictoRepository;
import org.seedstack.pv2.domain.pictofamily.PictoFamily;
import org.seedstack.pv2.domain.pictofamily.PictoFamilyRepository;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUses;
import org.seedstack.pv2.domain.ruleofuses.RuleOfUsesRepository;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawing;
import org.seedstack.pv2.domain.specificdrawing.SpecificDrawingRepository;
import org.seedstack.pv2.domain.type.Type;
import org.seedstack.pv2.domain.type.TypeRepository;
import org.seedstack.pv2.infrastructure.data.category.CategoryDTO;
import org.seedstack.pv2.infrastructure.data.color.ColorDTO;
import org.seedstack.pv2.infrastructure.data.image.ImageTypeDTO;
import org.seedstack.pv2.infrastructure.data.image.ImageTypeDTOAssembler;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTO;
import org.seedstack.pv2.infrastructure.data.picto.PictoDTOAssembler;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTO;
import org.seedstack.pv2.infrastructure.data.pictofamily.PictoFamilyDTOAssembler;
import org.seedstack.pv2.infrastructure.data.ruleofuses.RuleOfUsesDTO;
import org.seedstack.pv2.infrastructure.data.ruleofuses.RuleOfUsesDTOAssembler;
import org.seedstack.pv2.infrastructure.data.specificdrawing.SpecificDrawingDTO;
import org.seedstack.pv2.infrastructure.data.specificdrawing.SpecificDrawingDTOAssembler;
import org.seedstack.pv2.infrastructure.data.type.TypeDTO;
import org.seedstack.pv2.picxml.beans.PictoDescription;
import org.seedstack.pv2.picxml.beans.PictoDescription.AdaptationList;
import org.seedstack.pv2.picxml.beans.PictoDescription.DocumentList;
import org.seedstack.pv2.picxml.beans.PictoDescription.GeometryList;
import org.seedstack.pv2.picxml.beans.PictoDescription.PictoList;
import org.seedstack.pv2.utils.PictoUtils;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.Logging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class :Picto Service Implementation.
 */
public class PictoServiceImpl implements PictoService {

	/** The m picto assembler. */
	@Inject
	private PictoDTOAssembler m_PictoAssembler;

	/** The picto repository. */
	@Inject
	private PictoRepository pictoRepository;

	/** The m picto family assembler. */
	@Inject
	private PictoFamilyDTOAssembler m_PictoFamilyAssembler;

	/** The m picto repository. */
	@Inject
	private PictoRepository m_PictoRepository;

	/** The m picto family repository. */
	@Inject
	private PictoFamilyRepository m_PictoFamilyRepository;

	/** The m type repository. */
	@Inject
	private TypeRepository m_TypeRepository;

	/** The m color repository. */
	@Inject
	private ColorRepository m_ColorRepository;

	/** The m specific drawing assembler. */
	@Inject
	private SpecificDrawingDTOAssembler m_SpecificDrawingAssembler;

	/** The m specific drawing repository. */
	@Inject
	private SpecificDrawingRepository m_SpecificDrawingRepository;

	/** The m rules repository. */
	@Inject
	private RuleOfUsesRepository m_RulesRepository;

	/** The m rules of uses assembler. */
	@Inject
	private RuleOfUsesDTOAssembler m_RulesOfUsesAssembler;

	/** The m image repository. */
	@Inject
	private ImageTypeRepository m_ImageRepository;

	/** The m image type assembler. */
	@Inject
	private ImageTypeDTOAssembler m_ImageTypeAssembler;

	/** The m category repository. */
	@Inject
	private CategoryRepository m_CategoryRepository;

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(PictoServiceImpl.class);

	/** The pic xml directory. */
	@Configuration("com.inetpsa.pv2.xml.file.xml.path")
	private String picXmlDirectory;

	/** The pic xml target. */
	@Configuration("com.inetpsa.pv2.target.picto.file.target")
	private String picXmlTarget;

	/** List to store the XML file path. */
	private static List<File> result = new ArrayList<File>();

	/** reference number of picto. */
	private String referenceNum;

	/** Picto name. */
	private String pictoName;

	/** The success count. */
	private int successCount = 0;

	/** The failure count. */
	private int failureCount = 0;

	/** List to failure data. */
	private static List<String> failList = new ArrayList<String>();

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.PictoService#startDataMigartion()
	 */
	@Override
	public void startDataMigartion() {

		File file = null;

		// Directory where the files are located
		try {
			logger.info("**** Picto File Source Directory :" + picXmlDirectory);
			file = new File(picXmlDirectory);
			if (!file.getPath().isEmpty()) {
				if (file.canRead()) {
					getAllDirectoryInfo(file);
				} else {
					logger.warn("**** File Source Directory does not have Access Permission");
				}
			} else {
				logger.warn("**** File Source Directory not exist");
			}

		} catch (IllegalArgumentException e) {
			logger.error("**** Exception occured while reading File Source Directory", e);
		}

	}

	/**
	 * Get all the information from the source directory.
	 *
	 * @param path the path
	 * @return the all directory info
	 */
	public void getAllDirectoryInfo(File path) {
		boolean xmlResponce = false;
		boolean responce = true;
		// Search the xml file path in a directory
		logger.info("**** Search the xml file from directory");
		responce = searchXml(path);
		logger.info("**** Total number of Xml file in Directory: " + result.size());
		if (responce == false && result.isEmpty()) {
			logger.error("**** There is no XML at given path " + path);
		} else {
			try {
				for (File file : result) {
					String extension = FilenameUtils.getExtension(file.getName());

					String preFilePath = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath());
					String filename = FilenameUtils.getBaseName(file.getAbsolutePath());
					File target = new File(picXmlTarget);

					if (!target.getPath().isEmpty() || (target.canRead() && target.canWrite())) {
						File targetPath = new File(picXmlTarget + PictoConstants.FILE_SEPARATOR + filename);
						if (PictoConstants.FILE_PICXML.equals(extension)) {
							xmlResponce = createObjectbyXml(file, filename);
							try {
								if (xmlResponce) {
									logger.info("==============================================================");
									logger.info("######### Start: Copying the file to Destination  ############ ");
									logger.info("==============================================================");

									copyFolderData(new File(preFilePath), targetPath);

									logger.info("==============================================================");
									logger.info("################### End: Copy files  #########################");
									logger.info("==============================================================");
								}
							} catch (IOException e) {
								logger.error(" **** End: Exception occured copy file ", e);
							}

						}
					} else {
						logger.warn("**** Target folder not exist or doesn't have access permission");
					}
				}
			} catch (Exception e) {
				logger.error("**** Excetption occured while set the DTO values ", e);
			}
		}
		logger.info(" ************************* RESULT ***********************");
		logger.info("**** Total Number Success : " + successCount);
		logger.info("**** Total Number Failure : " + failureCount);

		logger.info("********************TOTAL FAILURE DATA ********************");
		for (String str : failList) {
			logger.info(str);
		}
	}

	/**
	 * Create the object by reading the XML file.
	 *
	 * @param path the path
	 * @param targetPath the target path
	 * @return true, if successful
	 */
	public boolean createObjectbyXml(File path, String targetPath) {
		PictoDescription picObj = null;
		boolean updateResponce = false;
		try {
			logger.info("**** XML Paths " + path);
			// Unmarshall the xml file
			picObj = Pv2JaxBWrapper.unmarshal(path);
		} catch (JAXBException e) {
			logger.error("**** Exception occured while Unmarshalling", e);
		}

		if (picObj == null) {
			failureCount++;
			failList.add("**** Failed while Unmarshalling the XML File:" + path);
			return updateResponce;
		}
		String filePath = FilenameUtils.getFullPathNoEndSeparator(path.getAbsolutePath());
		// Get refnum, name from Xml file.
		try {
			String filename = path.getName();
			filename = filename.substring(0, filename.lastIndexOf("."));
			StringTokenizer st = new StringTokenizer(filename, "_");
			while (st.hasMoreTokens()) {
				referenceNum = st.nextToken();
				pictoName = st.nextToken();
			}
		} catch (Exception e) {
			logger.error(" **** File name doesn't have standard format", e);
		}

		// Convert the un marshalled object to specific DTO
		try {
			logger.info("===============================================================================");
			logger.info(" ************************* START Assign the values to DTO *********************");
			logger.info("===============================================================================");

			Set<PictoDTO> pictoDtoSet = new HashSet<PictoDTO>(convertPicto(picObj, filePath, targetPath));

			Set<RuleOfUsesDTO> ruleOfUsesSet = new HashSet<RuleOfUsesDTO>(convertRuleofUses(picObj));

			PictoFamilyDTO pictoFamily = convertPictoFamilyDto(picObj);
			pictoFamily.setPictos(pictoDtoSet);
			pictoFamily.setRules(ruleOfUsesSet);

			logger.info("===============================================================================");
			logger.info(" ************************* END of Assign the values to DTO *********************");
			logger.info("================================================================================");

			updateResponce = updatePictoFamily(pictoFamily);
			/* SN - GL - 264 - 22-Jul-16 - Start */
			if (updateResponce) {
				successCount++;
			} else {
				failureCount++;
			}
			/* SN - GL - 264 - 22-Jul-16 - End */
			return updateResponce;

		} catch (Exception e) {
			logger.error("**** Exception occured while set the DTO values", e);
			failureCount++;
			failList.add("**** Failed on Assigning the values to DTO:" + path);
			return updateResponce;
		}

	}

	/**
	 * Copy folder data.
	 *
	 * @param source the source
	 * @param target the target
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void copyFolderData(File source, File target) throws IOException {
		if (source.isDirectory() && !(source.getName().trim().equalsIgnoreCase(PictoConstants.BACK_FILE))) {
			PictoUtils.makeDirectory(target);

			File[] listOfFiles = source.listFiles();
			for (int j = 0; j < listOfFiles.length; j++) {

				if (!(listOfFiles[j].isDirectory()) && !listOfFiles[j].getName().contains(PictoConstants.PDF_EXT)) {
					String extension = FilenameUtils.getExtension(listOfFiles[j].getName());
					if (PictoConstants.AI_IMG.equals(extension) || PictoConstants.IGS_IMG.equals(extension) || PictoConstants.JPG_IMG.equals(extension) || PictoConstants.PNG_IMG.equals(extension)) {
						ImageExtension exten = ImageExtension.valueOf(extension);
						switch (exten) {
							case ai:
								copyFileData(listOfFiles[j], target, listOfFiles[j].getName());
								break;
							case igs:
								copyFileData(listOfFiles[j], target, listOfFiles[j].getName());
								break;
							case jpg:
								copyFileData(listOfFiles[j], target, listOfFiles[j].getName());
								break;
							case png:
								if (listOfFiles[j].getName().contains(PictoConstants.PNG_EXT)) {
									String pngFile = listOfFiles[j].getName().replace(PictoConstants.PNG_EXT, PictoConstants.FILE_PNG);
									copyFileData(listOfFiles[j], target, pngFile);
								}
								break;
						}

					}
				} else {
					if (PictoConstants.AI_PUBLIC_FILE.equalsIgnoreCase(listOfFiles[j].getName().trim())) {
						File[] listOfAIFile = listOfFiles[j].listFiles();

						for (int k = 0; k < listOfAIFile.length; k++) {
							copyFileData(listOfAIFile[k], target, listOfAIFile[k].getName());
						}
					}

				}

			}

		}
	}

	/**
	 * Copy file.
	 *
	 * @param source the source
	 * @param target the target
	 * @param fileName the file name
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void copyFileData(File source, File target, String fileName) throws IOException {

		String folder = FilenameUtils.getBaseName(target.getAbsolutePath());
		String version = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));
		String medTarget;
		String tinyTarget;
		String littleTarget;

		if (PictoConstants.Medium.equalsIgnoreCase(version) || PictoConstants.Tiny.equalsIgnoreCase(version) || PictoConstants.Little.equalsIgnoreCase(version)) {
			medTarget = target + File.separator + folder + "_" + PictoConstants.Medium;
			tinyTarget = target + File.separator + folder + "_" + PictoConstants.Tiny;
			littleTarget = target + File.separator + folder + "_" + PictoConstants.Little;
		} else {
			medTarget = target + File.separator + folder + "_" + PictoConstants.Medium + "_" + version;
			tinyTarget = target + File.separator + folder + "_" + PictoConstants.Tiny + "_" + version;
			littleTarget = target + File.separator + folder + "_" + PictoConstants.Little + "_" + version;
		}

		if (fileName.contains(PictoConstants.LITTLE_EXT)) {
			copyFileContent(source, fileName, littleTarget);

		}

		if (fileName.contains(PictoConstants.TINY_EXT)) {
			copyFileContent(source, fileName, tinyTarget);
		}

		if (fileName.contains(PictoConstants.MID_EXT)) {
			copyFileContent(source, fileName, medTarget);
		}

	}

	/**
	 * Create a FileFilter that matches ".picxml" files
	 */
	private static FileFilter filter = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return file.isFile() && file.getName().endsWith(PictoConstants.FILE_SUFFIX);
		}
	};

	/**
	 * Search the file in specified folder/sub folder and save the file in result list.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
	public static boolean searchXml(File file) {
		File[] paths = null;
		try {
			File[] files = file.listFiles();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory() && !(PictoConstants.BACK_FILE.equalsIgnoreCase(files[i].getName().trim()))) {
						// Search for files if its directory
						searchXml(files[i]);
					} else {
						// Filter with given .extension files
						paths = file.listFiles(filter);
						if (!result.containsAll(Arrays.asList(paths))) {
							result.addAll(Arrays.asList(paths));
						}

					}
				}
			} else {
				logger.warn("**** File Source Directory is empty :" + file);
			}
			return PictoConstants.SUCCESS;
		} catch (Exception e) {
			logger.error("**** Exception while reading the Xml:", e);
			return PictoConstants.FAILURE;
		}

	}

	/**
	 * Converted picto family dto.
	 *
	 * @param pic the pic
	 * @return PictoFamilyDTO
	 */
	public PictoFamilyDTO convertPictoFamilyDto(PictoDescription pic) {

		logger.info("**** START: Assign the value to Picto Family DTO ");
		PictoFamilyDTO picFamilyDTO = new PictoFamilyDTO();
		try {
			picFamilyDTO.setReferenceNum(referenceNum);
			picFamilyDTO.setName(pictoName);
			if (pic.getGeneral().getStatus().isEmpty()) {
				picFamilyDTO.setInformationType(PictoConstants.TYPE_NOTHING);
			}
			if (PictoConstants.INFO_VALID.equals(pic.getGeneral().getStatus())) {
				picFamilyDTO.setInformationType(PictoConstants.TYPE_VALID);
			}
			if (PictoConstants.INFO_WARN.equals(pic.getGeneral().getStatus())) {
				picFamilyDTO.setInformationType(PictoConstants.TYPE_WARNING);
			}
			if (PictoConstants.INFO_READ.equals(pic.getGeneral().getStatus())) {
				picFamilyDTO.setInformationType(PictoConstants.TYPE_INFO);
			}

			picFamilyDTO.setInformationFR("");
			picFamilyDTO.setInformationEN("");

			if (pic.getPictoList() != null && pic.getPictoList().getPicto() != null && !pic.getPictoList().getPicto().isEmpty()) {
				PictoDescription.PictoList.Picto picto = pic.getPictoList().getPicto().get(0);
				if (picto.getWorkInProgress() == 1) {
					picFamilyDTO.setValidationLevel(PictoConstants.LEVEL_VALID);
				} else {
					picFamilyDTO.setValidationLevel(PictoConstants.LEVEL_INPROGRESS);
				}
			}

			if (!pic.getPrivateMessage().getText().isEmpty()) {
				picFamilyDTO.setAdminInfo(pic.getPrivateMessage().getText());
			}

			GeometryList geometricList = pic.getGeometryList();
			List<PictoDescription.GeometryList.Geometry> list = geometricList.getGeometry();
			for (PictoDescription.GeometryList.Geometry geometry : list) {
				if (!geometry.getName().isEmpty()) {
					picFamilyDTO.setFunctionFR(geometry.getName());
				}
			}

			TypeDTO type = new TypeDTO();
			type.setTypeLabel(pic.getGeneral().getType());
			picFamilyDTO.setTypeID(type);

			if (!pic.getGeneral().getCharterName().isEmpty()) {
				picFamilyDTO.setRefCharte(pic.getGeneral().getCharterName());
			}
			if (pic.getRHNCmd() != null && PictoConstants.PIC_XML_STATUS.equals(pic.getRHNCmd().getStatus())) {
				picFamilyDTO.setCommand(PictoConstants.SUCCESS);
			} else {
				logger.warn("**** RHNCmd tag not present in Xml");
				picFamilyDTO.setCommand(PictoConstants.FAILURE);
			}
			picFamilyDTO.setCommandInformation("");

			if (pic.getRHNTémoins() != null && PictoConstants.PIC_XML_STATUS.equals(pic.getRHNTémoins().getStatus())) {

				picFamilyDTO.setIsRHNWitness(PictoConstants.SUCCESS);

				/* To set RHNActivation */
				if (PictoConstants.PIC_XML_STATUS.equals(pic.getRHNTémoins().getRHNActivationStatus())) {
					picFamilyDTO.setIsRHNActive(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNTémoins().getRHNActivationColor());
					picFamilyDTO.setWitnessActive(color);
				} else if (pic.getRHNActivation() != null && PictoConstants.PIC_XML_STATUS.equals(pic.getRHNActivation().getStatus())) {
					picFamilyDTO.setIsRHNActive(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNActivation().getColor());
					picFamilyDTO.setWitnessActive(color);

				} else {
					picFamilyDTO.setIsRHNActive(PictoConstants.FAILURE);
				}

				/* To set RHNAlerte */
				if (PictoConstants.PIC_XML_STATUS.equals(pic.getRHNTémoins().getRHNAlerteStatus())) {
					picFamilyDTO.setIsRHNAlert(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNTémoins().getRHNAlerteColor());
					picFamilyDTO.setWitnessAlert(color);
				} else if (pic.getRHNAlerte() != null && PictoConstants.PIC_XML_STATUS.equals(pic.getRHNAlerte().getStatus())) {
					picFamilyDTO.setIsRHNAlert(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNAlerte().getColor());
					picFamilyDTO.setWitnessActive(color);

				} else {
					picFamilyDTO.setIsRHNAlert(PictoConstants.FAILURE);
				}
				/* To set RHNDefaillance */
				if (PictoConstants.PIC_XML_STATUS.equals(pic.getRHNTémoins().getRHNDefaillanceStatus())) {
					picFamilyDTO.setIsRHNDefault(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNTémoins().getRHNDefaillanceColor());
					picFamilyDTO.setWitnessFailure(color);
				} else if (pic.getRHNDefaillance() != null && PictoConstants.PIC_XML_STATUS.equals(pic.getRHNDefaillance().getStatus())) {
					picFamilyDTO.setIsRHNDefault(PictoConstants.SUCCESS);
					ColorDTO color = new ColorDTO();
					color.setColor(pic.getRHNDefaillance().getColor());
					picFamilyDTO.setWitnessActive(color);

				} else {
					picFamilyDTO.setIsRHNDefault(PictoConstants.FAILURE);
				}
			} else {
				picFamilyDTO.setIsRHNWitness(PictoConstants.FAILURE);
				picFamilyDTO.setIsRHNActive(PictoConstants.FAILURE);
				picFamilyDTO.setIsRHNAlert(PictoConstants.FAILURE);
				picFamilyDTO.setIsRHNDefault(PictoConstants.FAILURE);
				logger.warn("**** RHNTémoins tag not present in Xml");
			}

			if (pic.getRHNInfo() != null && !pic.getRHNInfo().getText().isEmpty()) {
				picFamilyDTO.setRhnInfoFR(pic.getRHNInfo().getText());

			} else {
				logger.warn("**** RHNInfo tag not present in Xml");
			}

			CategoryDTO cat = new CategoryDTO();
			cat.setName(pic.getGeneral().getSubCategory());
			long catId = pic.getGeneral().getCategory();
			picFamilyDTO.setCategoryID(catId);
			if (pic.getKeywordList() != null && !pic.getKeywordList().getText().isEmpty()) {
				picFamilyDTO.setKeywordFR(pic.getKeywordList().getText());
				picFamilyDTO.setKeywordEN("TODO");
			} else {
				picFamilyDTO.setKeywordFR("TODO");
				picFamilyDTO.setKeywordEN("TODO");
			}
			logger.info("**** End: Assign the value to Picto Family DTO ");

		} catch (Exception e) {
			logger.error("Exception while assiging the value to family DTO ", e);
			picFamilyDTO = null;
		}
		return picFamilyDTO;
	}

	/**
	 * Convert Picto Description to list of pictoDTO object.
	 *
	 * @param pic the pic
	 * @param sourcePath the source path
	 * @param targetPath the target path
	 * @return picObjectList
	 */
	public List<PictoDTO> convertPicto(PictoDescription pic, String sourcePath, String targetPath) {
		logger.info("**** START Assign the value to Picto DTO ");
		List<PictoDTO> picObjectList = new ArrayList<PictoDTO>();
		try {
			if (pic.getPictoList() != null) {
				PictoList picList = pic.getPictoList();
				List<PictoDescription.PictoList.Picto> picListInfo = picList.getPicto();
				for (PictoDescription.PictoList.Picto picto : picListInfo) {
					PictoDTO picDTO = new PictoDTO();

					if ("Reference".equals(picto.getClazz())) {
						picDTO.setIsFrontagePicto(true);
					} else {
						picDTO.setIsFrontagePicto(false);
					}

					if (picto.getVisibility() == 1) {
						picDTO.setIsVisible(true);
					} else {
						picDTO.setIsVisible(false);
					}
					String variants = picto.getSuffix();
					if (variants.contains("_")) {
						picDTO.setVariantType(variants.substring(0, variants.indexOf("_")));
						picDTO.setVersion(variants.substring(variants.lastIndexOf("_") + 1));
					} else {
						picDTO.setVariantType(picto.getSuffix());

					}

					picDTO.setCreateDate(new Date());
					picDTO.setModifyDate(new Date());

					picDTO.setImageLocation(targetPath);
					Set<ImageTypeDTO> image = new HashSet<ImageTypeDTO>(convertedImageType(sourcePath, picto.getSuffix()));
					picDTO.setImageTypes(image);
					picObjectList.add(picDTO);

				}
			} else {
				logger.warn("**** PictoList tag not present in Xml");
			}
			logger.info("**** End: Assign the value to Picto DTO ");

		} catch (Exception e) {
			picObjectList = null;
			logger.error("Exception while assiging the value to Picto DTO ", e);
		}
		return picObjectList;
	}

	/**
	 * Convert picto Description to list of SpecificDrawingDTO object.
	 *
	 * @param pic the pic
	 * @return the spdObjectList
	 */
	public List<SpecificDrawingDTO> convertedSpecificDrawing(PictoDescription pic) {

		logger.info("**** START: Assign the value to Specific Drawing DTO  ");
		List<SpecificDrawingDTO> spdObjectList = new ArrayList<SpecificDrawingDTO>();
		try {
			if (pic.getAdaptationList() != null) {
				AdaptationList adaptList = pic.getAdaptationList();
				List<PictoDescription.AdaptationList.Adaptation> adaptListInfo = adaptList.getAdaptation();
				for (PictoDescription.AdaptationList.Adaptation adaption : adaptListInfo) {
					SpecificDrawingDTO spDTO = new SpecificDrawingDTO();
					spDTO.setName(adaption.getUrl());
					spDTO.setCommentsFR(adaption.getCommentaires());
					spDTO.setCommentsEN(adaption.getCommentaires());
					spdObjectList.add(spDTO);
				}
			} else {
				logger.warn("**** AdaptationList tag not present in Xml");
			}
			logger.info("**** End: Assign the value to Specific Drawing DTO  ");
		} catch (Exception e) {
			logger.error("Exception while assiging the value to specific drawing DTO ", e);
			spdObjectList = null;
		}

		return spdObjectList;
	}

	/**
	 * Convert picto Description to list of Rule of uses object.
	 *
	 * @param pic the pic
	 * @return the rouObjectList
	 */
	public List<RuleOfUsesDTO> convertRuleofUses(PictoDescription pic) {

		logger.info("**** START: Assign the value to Rule of Uses DTO  ");
		List<RuleOfUsesDTO> rouObjectList = new ArrayList<RuleOfUsesDTO>();
		try {
			if (pic.getDocumentList() != null) {
				DocumentList docList = pic.getDocumentList();
				List<PictoDescription.DocumentList.Document> rouListInfo = docList.getDocument();
				for (PictoDescription.DocumentList.Document doc : rouListInfo) {
					RuleOfUsesDTO rouDTO = new RuleOfUsesDTO();
					rouDTO.setName(doc.getName());
					rouDTO.setDocLink(doc.getUrl());
					rouObjectList.add(rouDTO);
				}
			} else {
				logger.warn("**** DocumentList tag not present in Xml");
			}
			logger.info("**** END: Assign the value to Rule of Uses DTO  ");
		} catch (Exception e) {
			logger.error("Exception while assiging the value to rule of uses DTO ", e);
			rouObjectList = null;
		}
		return rouObjectList;
	}

	/**
	 * Save the Picto family information.
	 *
	 * @param pictoFamilyDTO the picto family DTO
	 * @return true, if successful
	 */
	@Override
	public boolean updatePictoFamily(PictoFamilyDTO pictoFamilyDTO) {
		logger.info("==============================================================");
		logger.info("################## START: Saving the Data  #################### ");
		logger.info("==============================================================");
		List<Color> colorActiveEntity = null;
		List<Color> colorAlertEntity = null;
		List<Color> colorFailureEntity = null;
		try {
			ColorDTO colorActiveDTO = pictoFamilyDTO.getWitnessActive();
			if (colorActiveDTO != null) {
				logger.info("**** Saving the  color for Activation ");
				colorActiveEntity = updateColor(colorActiveDTO);
			}

			ColorDTO colorAlertDTO = pictoFamilyDTO.getWitnessAlert();
			if (colorAlertDTO != null) {
				logger.info("**** Saving the  color for Alert ");
				colorAlertEntity = updateColor(colorAlertDTO);
			}

			ColorDTO colorFailureDTO = pictoFamilyDTO.getWitnessFailure();
			if (colorFailureDTO != null) {
				logger.info("**** Saving the  color for Defaillance ");
				colorFailureEntity = updateColor(colorFailureDTO);
			}

			String typeValue = pictoFamilyDTO.getTypeID().getTypeLabel();
			Type type = m_TypeRepository.findAllTypeByName(typeValue);

			Long categoryId = pictoFamilyDTO.getCategoryID();
			Category category = m_CategoryRepository.getCategoryById(categoryId);

			if (category != null) {
				PictoFamily pictoFamilyEntity = m_PictoFamilyRepository.findAllPictoFamilyByRefNumber(pictoFamilyDTO.getReferenceNum());
				if (pictoFamilyEntity == null) {
					logger.info("**** START: save the Picto Family ");
					pictoFamilyEntity = new PictoFamily();
					m_PictoFamilyAssembler.mergeAggregateWithDto(pictoFamilyEntity, pictoFamilyDTO);
					pictoFamilyEntity.setTypeID(type);
					pictoFamilyEntity.setCategoryID(category);
					pictoFamilyEntity.setWitnessActive(colorActiveEntity);
					pictoFamilyEntity.setWitnessAlert(colorAlertEntity);
					pictoFamilyEntity.setWitnessFailure(colorFailureEntity);
					m_PictoFamilyRepository.persistPictoFamily(pictoFamilyEntity);
					logger.info("**** END: Save the Picto Family with reference number :" + pictoFamilyEntity.getReferenceNum());
				} else {
					logger.warn("**** Picto Family with reference number already exist in Database: " + pictoFamilyDTO.getReferenceNum());
				}
				// Save the Picto information
				for (Iterator<PictoDTO> iterator = pictoFamilyDTO.getPictos().iterator(); iterator.hasNext();) {
					PictoDTO pictoDTO = (PictoDTO) iterator.next();
					updatePicto(pictoDTO, pictoFamilyEntity);

				}
				// Save the Rules of uses
				for (Iterator<RuleOfUsesDTO> iterator = pictoFamilyDTO.getRules().iterator(); iterator.hasNext();) {
					RuleOfUsesDTO rulesDTO = (RuleOfUsesDTO) iterator.next();
					updateRules(rulesDTO, pictoFamilyEntity);

				}
				logger.info("==============================================================");
				logger.info("################## END: Saving the Data  #################### ");
				logger.info("==============================================================");

				logger.info("~~~~~~~~~~~~~~~~~~Picto Imported Succesfully~~~~~~~~~~~~~~~~~~");

				return PictoConstants.SUCCESS;
			} else {
				failList.add("**** Failed while save Picto Family due to Category ID is null");
				return PictoConstants.FAILURE;
			}
		} catch (Exception e) {
			logger.error(" **** End: Exception occured while saving the data", e);
			failList.add("**** Failed while save Picto Family information for Reference Number: " + pictoFamilyDTO.getReferenceNum());
			return PictoConstants.FAILURE;

		}
	}

	/**
	 * Save the Picto information.
	 *
	 * @param pictoDTO the picto DTO
	 * @param picFamilyEnyity the pic family enyity
	 */
	public void updatePicto(PictoDTO pictoDTO, PictoFamily picFamilyEnyity) {
		try {
			Picto pictoEntity = m_PictoRepository.findAllPictoByFamilyID(picFamilyEnyity, pictoDTO.getVariantType(), pictoDTO.getVersion());
			if (pictoEntity == null) {
				logger.info("**** START: Save the Picto ");
				pictoEntity = new Picto();
				m_PictoAssembler.mergeAggregateWithDto(pictoEntity, pictoDTO);
				pictoEntity.setPictoFamilyID(picFamilyEnyity);
				m_PictoRepository.persistPicto(pictoEntity);
				logger.info("**** END: Save the Picto Id: " + pictoEntity.getEntityId());

			} else {
				logger.warn("**** Picto id is already exist in Database: " + pictoEntity.getEntityId());
			}
			// Save the image information
			for (Iterator<ImageTypeDTO> iterator = pictoDTO.getImageTypes().iterator(); iterator.hasNext();) {
				ImageTypeDTO imageoDTO = (ImageTypeDTO) iterator.next();
				updateImageType(imageoDTO, pictoEntity);

			}
		}

		catch (Exception e) {
			logger.error("**** Exception occured while saving the Picto:", e);
			failList.add("**** Failed while save picto information for reference number :" + picFamilyEnyity.getReferenceNum());
		}
	}

	/**
	 * Save the color information.
	 *
	 * @param colorDTO the color DTO
	 * @return the listColorEnity
	 */

	public List<Color> updateColor(ColorDTO colorDTO) {

		List<Color> listColorEntity = new ArrayList<Color>();
		Color colorEntity = null;

		try {
			String color = colorDTO.getColor();
			List<String> colorList = Arrays.asList(color.split(","));

			for (int i = 0; i < colorList.size(); i++) {
				colorEntity = m_ColorRepository.findColorByLable(colorList.get(i));
				listColorEntity.add(colorEntity);
			}
		} catch (Exception e) {
			logger.error("**** Exception occured while saving the Color:", e);
			failList.add("**** Failed while save color information :" + colorDTO.getColor());
		}
		return listColorEntity;

	}

	/**
	 * Save the specific drawing information.
	 *
	 * @param drawingDTO the drawing DTO
	 * @param picFamilyEntity the pic family entity
	 */
	public void updateSpecificDrawing(SpecificDrawingDTO drawingDTO, PictoFamily picFamilyEntity) {
		try {

			SpecificDrawing specDrawingEntiry = m_SpecificDrawingRepository.findSpeDrawingByFamilyId(picFamilyEntity, drawingDTO.getName());
			if (specDrawingEntiry == null) {
				logger.info("**** START: Save the SpecificDrawing  ");
				specDrawingEntiry = new SpecificDrawing();
				m_SpecificDrawingAssembler.mergeAggregateWithDto(specDrawingEntiry, drawingDTO);
				specDrawingEntiry.setFamilyId(picFamilyEntity);
				m_SpecificDrawingRepository.persistSpecificDrawing(specDrawingEntiry);
				logger.info("**** END: Saved the Specific Drawing Id : " + specDrawingEntiry.getEntityId());
			} else {
				logger.warn("**** Specific Drawing information is already exist in Database: " + specDrawingEntiry.getName());
			}
		} catch (Exception e) {
			logger.error("**** Exception occured while saving the SpecificDrawing:", e);
			failList.add("**** Failed while save Specific Drawing informatation for reference number :" + picFamilyEntity.getReferenceNum());
		}

	}

	/**
	 * Save the Rules information.
	 *
	 * @param ruleDTO the rule DTO
	 * @param picFamilyEntity the pic family entity
	 */
	public void updateRules(RuleOfUsesDTO ruleDTO, PictoFamily picFamilyEntity) {
		try {

			RuleOfUses ruleofUsesEntity = m_RulesRepository.findAllRuleByFamilyId(picFamilyEntity, ruleDTO.getName());
			if (ruleofUsesEntity == null) {
				logger.info("**** START: Save the RuleOfUses  ");
				ruleofUsesEntity = new RuleOfUses();
				m_RulesOfUsesAssembler.mergeAggregateWithDto(ruleofUsesEntity, ruleDTO);
				ruleofUsesEntity.setFamilyId(picFamilyEntity);
				m_RulesRepository.persistRule(ruleofUsesEntity);
				logger.info("**** END:Saved the Rules of Uses Id : " + ruleofUsesEntity.getEntityId());
			} else {
				logger.warn("**** Rule information is already exist in Database: " + ruleofUsesEntity.getName());
			}

		} catch (Exception e) {
			logger.error("**** Exception occured while saving the RuleOfUses:", e);
			failList.add("**** Failed while save Rule of Uses informatation for reference number :" + picFamilyEntity.getReferenceNum());
		}

	}

	/**
	 * Save the Image information.
	 *
	 * @param imageDTO the image DTO
	 * @param picto the picto
	 */
	public void updateImageType(ImageTypeDTO imageDTO, Picto picto) {
		try {
			ImageType imageTypeEntity = m_ImageRepository.findAllImageByPictoId(picto);
			if (imageTypeEntity == null) {
				logger.info("**** START save the Images  ");
				imageTypeEntity = new ImageType();
				m_ImageTypeAssembler.mergeAggregateWithDto(imageTypeEntity, imageDTO);
				imageTypeEntity.setPictoId(picto);
				ImageType image = m_ImageRepository.saveImageType(imageTypeEntity);
				logger.info("**** END: Saved the Image Id : " + image.getEntityId());
			} else {
				logger.warn("**** Image information is already exist in Database for Picto: " + imageTypeEntity.getPictoId().getEntityId());
			}
		} catch (Exception e) {
			logger.error("**** Exception occured while saving the Image type:", e);
			failList.add("**** Failed while save Image informatation for picto id :" + picto.getEntityId());
		}
	}

	/* (non-Javadoc)
	 * @see org.seedstack.pv2.application.PictoService#getAllPictos()
	 */
	@Override
	public List<PictoDTO> getAllPictos() {

		List<PictoDTO> l_PictoDTO = new ArrayList<PictoDTO>();

		List<Picto> l_picto = pictoRepository.getAllPictos();
		for (Picto picto : l_picto) {
			PictoDTO pictoDTO = new PictoDTO();
			m_PictoAssembler.assembleDtoFromAggregate(pictoDTO, picto);
			l_PictoDTO.add(pictoDTO);
		}

		return l_PictoDTO;

	}

	/**
	 * Convert to ImageType object.
	 *
	 * @param sourceFile the source file
	 * @param Varient the varient
	 * @return the list
	 */
	public List<ImageTypeDTO> convertedImageType(String sourceFile, String Varient) {
		File file = new File(sourceFile);
		File[] listOfFiles = file.listFiles();

		List<ImageTypeDTO> imageObjectList = new ArrayList<ImageTypeDTO>();
		ImageTypeDTO imageDTO = new ImageTypeDTO();
		logger.info("**** START: Assign the value to Image Type DTO  ");
		for (int i = 0; i < listOfFiles.length; i++) {
			String filename = listOfFiles[i].getName();

			if (!(listOfFiles[i].isDirectory()) && filename.contains(Varient)) {
				String extension = FilenameUtils.getExtension(listOfFiles[i].getName());
				if (PictoConstants.AI_IMG.equals(extension) || PictoConstants.IGS_IMG.equals(extension) || PictoConstants.JPG_IMG.equals(extension) || PictoConstants.PNG_IMG.equals(extension)) {
					ImageExtension exten = ImageExtension.valueOf(extension);
					switch (exten) {
						case ai:
							imageDTO.setImageAIWork(true);
							break;
						case igs:
							imageDTO.setImageIgs(true);
							break;
						case jpg:
							imageDTO.setImageJpg(true);
							break;
						case png:
							imageDTO.setImagePng(true);
							break;
					}
					imageObjectList.add(imageDTO);
				}
			}
			if ((listOfFiles[i].isDirectory()) && (PictoConstants.AI_PUBLIC_FILE.equalsIgnoreCase(listOfFiles[i].getName().trim()))) {
				File[] listOfAIFile = listOfFiles[i].listFiles();
				for (int j = 0; j < listOfAIFile.length; j++) {
					if (listOfAIFile[j].getName().contains(Varient)) {
						imageDTO.setImageAIPublic(true);
						imageObjectList.add(imageDTO);
					}
				}
			}

		}
		logger.info("**** End: Assign the value to Image Type DTO ");
		return imageObjectList;
	}

	/**
	 * Make directory path.
	 *
	 * @param file the file
	 * @param fileName the file name
	 * @return the string
	 */
	public String makeDirectoryPath(File file, String fileName) {

		String folderName = FilenameUtils.getBaseName(file.getAbsolutePath());
		String version = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.lastIndexOf("."));

		StringBuilder targetDirectory = new StringBuilder();

		if (PictoConstants.Medium.equalsIgnoreCase(version)) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Medium);
		} else if (!(PictoConstants.Tiny.equalsIgnoreCase(version) || PictoConstants.Little.equalsIgnoreCase(version))) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Medium).append("_").append(version);
		}

		if (PictoConstants.Tiny.equalsIgnoreCase(version)) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Tiny);
		} else if (!(PictoConstants.Medium.equalsIgnoreCase(version) || PictoConstants.Little.equalsIgnoreCase(version))) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Tiny).append(PictoConstants.PICTO_NAME_SEPARATOR).append(version);
		}

		if (PictoConstants.Little.equalsIgnoreCase(version)) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Little);
		} else if (!(PictoConstants.Medium.equalsIgnoreCase(version) || PictoConstants.Tiny.equalsIgnoreCase(version))) {
			targetDirectory.append(file).append(File.separator).append(folderName).append(PictoConstants.PICTO_NAME_SEPARATOR).append(PictoConstants.Little).append(PictoConstants.PICTO_NAME_SEPARATOR).append(version);
		}

		return targetDirectory.toString();
	}

	/**
	 * Copy file content.
	 *
	 * @param file the file
	 * @param fileName the file name
	 * @param targetDir the target dir
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void copyFileContent(File file, String fileName, String targetDir) throws IOException {

		PictoUtils.makeDirectory(new File(targetDir));

		if (!file.toString().contains(PictoConstants.AI_PUBLIC_FILE)) {
			FileUtils.copyFile(file, new File(targetDir + File.separator + fileName));
		} else {
			String newFile = fileName.substring(0, fileName.lastIndexOf(".")).concat(PictoConstants.AIP_EXT).concat(PictoConstants.FILE_AI);
			FileUtils.copyFile(file, new File(targetDir + File.separator + newFile));
		}

	}
}
