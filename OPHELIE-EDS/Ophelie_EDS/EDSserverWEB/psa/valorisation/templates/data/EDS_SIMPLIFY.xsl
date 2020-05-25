<?xml version="1.0" encoding="iso-8859-1"?>

<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:pxc="http://www.psa-peugeot-citroen.com/pxc/2.2.0"
    exclude-result-prefixes="pxc">

    <xsl:output method="xml" version="1.0" encoding="UTF8" indent="yes" />

    <xsl:strip-space elements="*" />

    <!-- template rule for nodes -->
    <xsl:template match="/">
        <xsl:text disable-output-escaping="yes"><![CDATA[<?xml-stylesheet type="text/xsl" href="EDS.xsl"?>]]></xsl:text>
        <xsl:apply-templates />
    </xsl:template>

    <!-- template rule for text and attribute nodes that copies text through: -->
    <xsl:template match="text()|@*">
        <xsl:value-of select="." />
    </xsl:template>

    <xsl:template match="formDatas">
    </xsl:template>

    <!-- template delete numbers96k -->
    <xsl:template match="numbers96k">
    </xsl:template>

    <xsl:template match="generic-data">
        <eds>
            <generic-data>
                <xsl:apply-templates select="record-model" />
                <xsl:apply-templates select="crea-date" />
                <xsl:apply-templates select="drift-validation-date" />
                <xsl:apply-templates select="drift" />
                <xsl:apply-templates select="is-bttbt" />
                <xsl:apply-templates select="major-version" />
                <xsl:apply-templates select="minor-version" />
                <xsl:apply-templates select="modif-date" />
                <xsl:apply-templates select="name" />
                <xsl:apply-templates select="project-setter-count" />
                <xsl:apply-templates select="perimeters" />
                <xsl:apply-templates select="project-setter" />
                <xsl:apply-templates select="follower-projects" />
                <xsl:apply-templates select="ref" />
                <xsl:apply-templates select="state" />
                <xsl:apply-templates select="supplier" />
                <xsl:apply-templates select="admin" />
                <xsl:apply-templates select="affectation" />
                <xsl:apply-templates select="officer" />
                <xsl:apply-templates select="validation-level" />
                <xsl:apply-templates select="numbers96kdef" />
                <xsl:apply-templates select="numbers96kreal" />
                <xsl:apply-templates select="URL" />
            </generic-data>
        </eds>
    </xsl:template>

    <xsl:template match="record-model">
        <record-model>
            <name>
                <xsl:for-each select="./name">
                    <xsl:call-template name="UNIT" />
                </xsl:for-each>
            </name>
        </record-model>
    </xsl:template>

    <xsl:template match="crea-date">
        <crea-date>
            <xsl:value-of select="." />
        </crea-date>
    </xsl:template>

    <xsl:template match="drift-validation-date">
        <drift-validation-date>
            <xsl:value-of select="." />
        </drift-validation-date>
    </xsl:template>

    <xsl:template match="drift">
        <drift>
            <xsl:value-of select="." />
        </drift>
    </xsl:template>

    <xsl:template match="is-bttbt">
        <is-bttbt>
            <xsl:value-of select="." />
        </is-bttbt>
    </xsl:template>

    <xsl:template match="major-version">
        <major-version>
            <xsl:value-of select="." />
        </major-version>
    </xsl:template>

    <xsl:template match="minor-version">
        <minor-version>
            <xsl:value-of select="." />
        </minor-version>
    </xsl:template>


    <xsl:template match="modif-date">
        <modif-date>
            <xsl:value-of select="." />
        </modif-date>
    </xsl:template>

    <xsl:template match="name">
        <name>
            <xsl:value-of select="." />
        </name>
    </xsl:template>



    <xsl:template match="project-setter-count">
        <project-setter-count>
            <xsl:value-of select="." />
        </project-setter-count>
    </xsl:template>

    <xsl:template match="perimeters">
        <perimeters>
            <perimeter>
                <is-active>
                    <xsl:value-of select="perimeter/is-active" />
                </is-active>
                <peName>
                    <xsl:value-of select="perimeter/peName" />
                </peName>
            </perimeter>
        </perimeters>
    </xsl:template>


    <xsl:template match="project-setter">
        <project-setter>
            <xsl:call-template name="MYPROJECT" />
        </project-setter>
    </xsl:template>

    <xsl:template match="follower-projects">
        <follower-projects>

            <xsl:for-each select="./follower-project/edsProject">
                <follower-project>
                    <edsProject>
                        <xsl:call-template name="MYPROJECT" />
                    </edsProject>
                    <pedsCount>
                        <xsl:value-of select="../pedsCount" />
                    </pedsCount>
                    <pedsReconductWithModif>
                        <xsl:value-of select="../pedsReconductWithModif" />
                    </pedsReconductWithModif>
                    <pedsReconductionDate>
                        <xsl:value-of select="../pedsReconductionDate" />
                    </pedsReconductionDate>

                </follower-project>
            </xsl:for-each>
        </follower-projects>
    </xsl:template>

    <xsl:template match="ref">
        <ref>
            <xsl:value-of select="." />
        </ref>
    </xsl:template>

    <xsl:template match="state">
        <state>
            <xsl:value-of select="." />
        </state>
    </xsl:template>

    <xsl:template match="supplier">
        <supplier>
            <is-active>
                <xsl:value-of select="is-active" />
            </is-active>
            <SName>
                <xsl:value-of select="SName" />
            </SName>
        </supplier>
    </xsl:template>

    <xsl:template match="admin">
        <admin>
            <xsl:call-template name="MYUSER" />
        </admin>
    </xsl:template>

    <xsl:template match="affectation">
        <affectation>
            <xsl:call-template name="MYUSER" />
        </affectation>
    </xsl:template>

    <xsl:template match="officer">
        <officer>
            <xsl:call-template name="MYUSER" />
        </officer>
    </xsl:template>

    <xsl:template match="validation-level">
        <validation-level>
            <xsl:value-of select="." />
        </validation-level>
    </xsl:template>

    <xsl:template match="numbers96kdef">
        <numbers96kdef>
            <xsl:for-each select="number96kdef">
                <number96kdef>
                    <reference>
                        <xsl:value-of select="pxc:TranscoRef(reference)" />
                    </reference>
                    <referenceRevision>
                        <xsl:value-of select="pxc:TranscoRef(reference)" />
                        <xsl:value-of select="pxc:TranscoVersion(reference,revision)" />
                    </referenceRevision>
                    <revision>
                        <xsl:value-of select="pxc:TranscoVersion(reference,revision)" />
                    </revision>
                </number96kdef>
            </xsl:for-each>
        </numbers96kdef>
    </xsl:template>

    <xsl:template match="numbers96kreal">
        <numbers96kreal>
            <xsl:for-each select="number96kreal">
                <number96kreal>
                    <reference>
                        <xsl:value-of select="pxc:TranscoRef(reference)" />
                    </reference>
                    <referenceRevision>
                        <xsl:value-of select="pxc:TranscoRef(reference)" />
                        <xsl:value-of select="pxc:TranscoVersion(reference,revision)" />
                    </referenceRevision>
                    <revision>
                        <xsl:value-of select="pxc:TranscoVersion(reference,revision)" />
                    </revision>
                </number96kreal>
            </xsl:for-each>
        </numbers96kreal>
    </xsl:template>

    <xsl:template match="URL">
        <URL>
            <xsl:value-of select="." />
        </URL>
    </xsl:template>

    <xsl:template name="UNIT">
        <WId>
            <xsl:value-of select="WId" />
        </WId>
        <WIndex>
            <xsl:value-of select="WIndex" />
        </WIndex>
        <WType>
            <xsl:value-of select="WType" />
        </WType>
        <value>
            <xsl:value-of select="value" />
        </value>
    </xsl:template>

    <xsl:template name="MYPROJECT">
        <prelim-stage>
            <xsl:for-each select="./prelim-stage">
                <xsl:call-template name="UNIT" />
            </xsl:for-each>
        </prelim-stage>

        <closed-stage>
            <xsl:for-each select="./closed-stage">
                <xsl:call-template name="UNIT" />
            </xsl:for-each>
        </closed-stage>

        <consolidate-stage>
            <xsl:for-each select="./consolidate-stage">
                <xsl:call-template name="UNIT" />
            </xsl:for-each>
        </consolidate-stage>
        <robust-stage>
            <xsl:for-each select="./robust-stage">
                <xsl:call-template name="UNIT" />
            </xsl:for-each>
        </robust-stage>
        <prelim-date>
            <xsl:value-of select="prelim-date" />
        </prelim-date>
        <closed-date>
            <xsl:value-of select="closed-date" />
        </closed-date>
        <robust-date>
            <xsl:value-of select="robust-date" />
        </robust-date>
        <consolidate-date>
            <xsl:value-of select="consolidate-date" />
        </consolidate-date>
        <index>
            <xsl:value-of select="index" />
        </index>
        <PName>
            <xsl:value-of select="PName" />
        </PName>
    </xsl:template>

    <xsl:template name="MYUSER">
        <edsRole>
            <roName>
                <xsl:value-of select="edsRole/roName" />
            </roName>
        </edsRole>
        <is-active>
            <xsl:value-of select="is-active" />
        </is-active>
        <UFirstname>
            <xsl:value-of select="UFirstname" />
        </UFirstname>
        <ULastname>
            <xsl:value-of select="ULastname" />
        </ULastname>
        <UPsaId>
            <xsl:value-of select="UPsaId" />
        </UPsaId>
        <UService>
            <xsl:value-of select="UService" />
        </UService>
    </xsl:template>

    <xsl:function name="pxc:TranscoRef">
        <xsl:param name="RefIni" />
        <xsl:value-of select="concat(substring($RefIni,1,8),80)" />
    </xsl:function>

    <xsl:function name="pxc:TranscoVersion">
        <xsl:param name="RefIni" />
        <xsl:param name="VersionIni" />
        <xsl:choose>
            <xsl:when test="string-length($RefIni) = 10">
                <!--Usecase 1 - 9th characters (Old PSA reference format) = ‘8’ or ‘7’ and 10th character (old PSA reference format) = an alphabetic character 
                    (For example = 8A) -->
                <xsl:choose>
                    <xsl:when
                        test="substring($RefIni,10,1)='A' or
                    substring($RefIni,10,1)='A' or
                    substring($RefIni,10,1)='B' or
                    substring($RefIni,10,1)='C' or
                    substring($RefIni,10,1)='D' or
                    substring($RefIni,10,1)='E' or
                    substring($RefIni,10,1)='F' or
                    substring($RefIni,10,1)='G' or
                    substring($RefIni,10,1)='H' or
                    substring($RefIni,10,1)='I' or
                    substring($RefIni,10,1)='J' or
                    substring($RefIni,10,1)='K' or
                    substring($RefIni,10,1)='L' or
                    substring($RefIni,10,1)='M' or
                    substring($RefIni,10,1)='N' or
                    substring($RefIni,10,1)='O' or
                    substring($RefIni,10,1)='P' or
                    substring($RefIni,10,1)='Q' or
                    substring($RefIni,10,1)='R' or
                    substring($RefIni,10,1)='S' or
                    substring($RefIni,10,1)='T' or
                    substring($RefIni,10,1)='U' or
                    substring($RefIni,10,1)='V' or
                    substring($RefIni,10,1)='W' or
                    substring($RefIni,10,1)='X' or
                    substring($RefIni,10,1)='Y' or
                    substring($RefIni,10,1)='Z'">

                        <!--12th character (New PSA reference format) = 10th character (Old PSA reference format) -->
                        <!--<xsl:value-of select="substring($RefIni,9,1)"/> -->
                        <xsl:choose>
                            <xsl:when test="substring($VersionIni,2,1)='.'">
                                <xsl:value-of select="concat(substring($RefIni,10,1),'.')" />
                            </xsl:when>

                            <xsl:when test="substring($VersionIni,2,1)='-'">
                                <xsl:value-of select="concat(substring($RefIni,10,1),'.')" />
                            </xsl:when>

                            <xsl:otherwise>
                                <xsl:value-of select="concat(substring($RefIni,10,1),substring($VersionIni,2,1))" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:when>

                    <!--Usecase2 - 9th and et 10th characters (Old PSA reference format) = ‘80’ or ‘77’ -->
                    <xsl:otherwise>
                        <xsl:choose>
                            <!-- ‘§§’ if version (Old PSA reference format ) = ‘**’ -->
                            <xsl:when test="substring($VersionIni,1,2)='**'">
                                <xsl:text>&#167;&#167;</xsl:text>
                            </xsl:when>
                            <!-- 12th and 13th character (New PSA reference format) = ? 12th and 13th character (Old PSA reference format) whatever value 
                                of the version -->

                            <xsl:otherwise>
                                <xsl:value-of select="substring($VersionIni,1,2)" />
                            </xsl:otherwise>
                        </xsl:choose>
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:when>
            <xsl:otherwise>
                <xsl:attribute name="userf2"><xsl:value-of select="$VersionIni" /></xsl:attribute>
            </xsl:otherwise>
        </xsl:choose>
    </xsl:function>
    <!-- Fin de Description -->
</xsl:stylesheet>
