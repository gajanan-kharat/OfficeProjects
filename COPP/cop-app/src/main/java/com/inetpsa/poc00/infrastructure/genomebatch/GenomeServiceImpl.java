package com.inetpsa.poc00.infrastructure.genomebatch;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.seedstack.jpa.JpaUnit;
import org.seedstack.seed.Logging;
import org.seedstack.seed.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.inetpsa.poc00.Config;
import com.inetpsa.poc00.application.GenomeService;
import com.inetpsa.poc00.constant.genomeconstant.GenomeConstant;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionary;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryDto;
import com.inetpsa.poc00.domain.genomelcdvdictionary.GenomeLCDVDictionaryRepository;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRule;
import com.inetpsa.poc00.domain.genometvvrule.GenomeTVVRuleRepository;

/**
 * The Class GenomeServiceImpl.
 */
/*
 * Implementation Class for GenomeService Interface
 * 
 * Process All file for Genome Batch
 */
public class GenomeServiceImpl implements GenomeService, GenomeConstant {

	/** The logger. */
	@Logging
	private static Logger logger = LoggerFactory.getLogger(GenomeServiceImpl.class);

	/** The tvv rule repo. */
	@Inject
	private GenomeTVVRuleRepository tvvRuleRepo;

	/** The dictionary repo. */
	@Inject
	private GenomeLCDVDictionaryRepository dictionaryRepo;

	/** The l_tvv rule file data. */
	List<GenomeTVVRule> l_tvvRuleFileData;

	/** The l_k fam dictionary file data. */
	List<GenomeLCDVDictionary> l_kFamDictionaryFileData;

	/** The l_family dic file data. */
	List<GenomeLCDVDictionaryDto> l_familyDicFileData;

	/** The gnrl dic file data object. */
	GenomeLCDVDictionaryDto gnrlDicFileDataObject;

	/* (non-Javadoc)
	 * @see com.inetpsa.poc00.application.GenomeService#FileProcessor(java.util.Map)
	 */
	@Override
	@Transactional
	@JpaUnit(Config.JPA_UNIT)
	public void fileProcessor(Map<String, File> file) throws IOException {

		TVVFile tvvFile = new TVVFile();
		KmatFamDictionaryFile kmatfamFile = new KmatFamDictionaryFile();
		GeneralDictionaryFile generalFile = new GeneralDictionaryFile();
		FamilyDictionaryFile familyFile = new FamilyDictionaryFile();

		/*
		 * General Dictionary File
		 */
		File commonFile = file.get(GENERAL_FILE_ID);
		generalFile.getGeneralDictionaryFile(commonFile, dictionaryRepo);

		/*
		 * KMAT FAM Dictionary File
		 */
		commonFile = file.get(KMAT_FAM_FILE_ID);
		kmatfamFile.getKmatFamDictionaryFile(commonFile, dictionaryRepo);

		/*
		 * Family Dictionary File
		 */
		commonFile = file.get(FAMILY_DIC_FILE_ID);
		familyFile.getFamilyDictionaryFile(commonFile, dictionaryRepo);

		/*
		 * TVV Rule File
		 */
		commonFile = file.get(TVV_FILE_ID);
		tvvFile.getTvvRuleFile(commonFile, tvvRuleRepo);

	}

}
