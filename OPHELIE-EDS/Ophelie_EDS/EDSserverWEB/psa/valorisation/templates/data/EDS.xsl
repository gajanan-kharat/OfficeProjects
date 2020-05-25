<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:o="urn:schemas-microsoft-com:office:office" exclude-result-prefixes="o">
	<xsl:output method="html" encoding="UTF-8" />

	<xsl:template match="/">
		<html>
			<meta charset="UTF-8"/>
			<body>
				<p>
					<table width="93%" border="1" style="font-size: 8pt;">
						<thead>
							<tr style="background-color: #CCDDFF;font-size: 8pt;" align="center">
								<td width="30%">ID</td>
								<td width="65%">Valeur</td>
							</tr>
						</thead>
						<tbody>
						
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'URL EDS:'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/URL" />	 
						 </xsl:call-template>								

						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'EDS Ref'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/ref" />		 
						 </xsl:call-template>	
							
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Index EDS '" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/record-model/name/WIndex" />			 
						 </xsl:call-template>								
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Type'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/record-model/name/WType" />		 
						 </xsl:call-template>				
				
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Type d organe'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/record-model/name/value" />		 
						 </xsl:call-template>							
							 
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Date de creation:'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/crea-date" />		 
						 </xsl:call-template>								
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'EDS en derive'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/drift" />		 
						 </xsl:call-template>								
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Connectee au réseau BT/TBT?'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/is-bttbt" />		 
						 </xsl:call-template>								
						 
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Vmajeur EDS'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/major-version" />		 
						 </xsl:call-template>								
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Vmineur EDS'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/minor-version" />		 
						 </xsl:call-template>								
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Date de modification'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/modif-date" />		 
						 </xsl:call-template>			
									
						 <xsl:call-template name="Ligne">
								 <xsl:with-param name="ID" select="'Nom EDS'" />
								 <xsl:with-param name="Valeur" select="eds/generic-data/name" />	 
						 </xsl:call-template>		
						
						 <xsl:call-template name="LigneIterater">
								 <xsl:with-param name="ID" select="'96xxx def'" />
								 <xsl:with-param name="ValeurCollection" select="eds/generic-data/numbers96kdef/number96kdef" />
						 </xsl:call-template>	
				 							
						 <xsl:call-template name="LigneIterater">
								 <xsl:with-param name="ID" select="'96xxx real'" />
								 <xsl:with-param name="ValeurCollection" select="eds/generic-data/numbers96kreal/number96kreal" />
						 </xsl:call-template>								

						</tbody>
					</table>
				</p>
				<p>
					<span lang="FR" style="font-size: 6pt; ">
					V0.1
				</span>
				</p>
			</body>
		</html>
	</xsl:template>
	<!-- ===========================================================================================================-->
	
	
<xsl:template name="Ligne">
		<xsl:param name="ID" select="0"/>
		<xsl:param name="Valeur" select="1"/>
		<tr>
			<td>
				<xsl:value-of select="$ID"/>
			</td>
			<td>
				<xsl:value-of select="$Valeur"/>
			</td>
		</tr>
		
</xsl:template>	
	
<xsl:template name="LigneIterater">
		<xsl:param name="ID" select="0"/>
		<xsl:param name="ValeurCollection" select="1"/>
		<tr>
			<td>
				<xsl:value-of select="$ID"/>
			</td>
			<td>
				<xsl:for-each select="$ValeurCollection">
						<xsl:value-of select="referenceRevision"/> <br/>
				</xsl:for-each>
			</td>
		</tr>
		
</xsl:template>	
	
	
	
</xsl:stylesheet>


