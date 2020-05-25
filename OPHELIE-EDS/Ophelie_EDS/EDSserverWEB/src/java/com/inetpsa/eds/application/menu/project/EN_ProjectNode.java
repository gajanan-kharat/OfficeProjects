package com.inetpsa.eds.application.menu.project;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.inetpsa.eds.application.EdsApplicationController;
import com.inetpsa.eds.application.actionbar.addnew.AddNewEdsButton;
import com.inetpsa.eds.application.actionbar.chs.ExportExcelCHS;
import com.inetpsa.eds.application.actionbar.export.ExportButton;
import com.inetpsa.eds.application.actionbar.export.I_Exportable;
import com.inetpsa.eds.application.actionbar.exportfc.ExportFCButton;
import com.inetpsa.eds.application.actionbar.exportfc.I_ExportableFC;
import com.inetpsa.eds.application.actionbar.exportxml.A_ExportExcel.ExcelExportException;
import com.inetpsa.eds.application.actionbar.exportxml.ExportExcelEDS;
import com.inetpsa.eds.application.content.project.ProjectView;
import com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode;
import com.inetpsa.eds.application.menu.edsnode.EN_FormNode;
import com.inetpsa.eds.application.popup.WsTestWindow;
import com.inetpsa.eds.dao.EDSdao;
import com.inetpsa.eds.dao.E_AccessLocked;
import com.inetpsa.eds.dao.model.EdsEds;
import com.inetpsa.eds.dao.model.EdsProject;
import com.inetpsa.eds.dao.model.EdsRight;
import com.inetpsa.eds.tools.uri.FragmentManager;
import com.vaadin.terminal.StreamResource;
import com.vaadin.terminal.StreamResource.StreamSource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Window;

/**
 * Navigation node associated with the view of a project.
 * 
 * @author Geometric Ltd.
 */
public class EN_ProjectNode extends A_EdsNavigationNode implements I_Exportable, I_ExportableFC {
    // PUBLIC
    /**
     * Parameterized constructor.
     * 
     * @param controller EDS application controller object.
     * @param project Project details.
     */
    public EN_ProjectNode(EdsApplicationController controller, EdsProject project) {
        super(controller);
        this.project = project;
        init();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getCaption()
     */
    @Override
    public String getCaption() {
        return project.getPName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#getURIfragment()
     */
    @Override
    public String getURIfragment() {
        return FragmentManager.formatURLFragmentForProject(project.getPId());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#areChildrenAllowed()
     */
    @Override
    public boolean areChildrenAllowed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onEnter()
     */
    @Override
    public void onEnter() {
        getController().getActionBar().clear();

        getController().showProject(project.getPId());
        getController().getActionBar().setLeftButtons(getLeftButtons());
        getController().getActionBar().setRightButtons(getRightButtons());
        // getController().getContent().addComponent( getView() );
        getController().setContent(getView());
        getController().getUriFragmentUtility().setFragment(getURIfragment(), false);
        getController().setCurrentProject(project);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.inetpsa.eds.application.menu.edsnode.A_EdsNavigationNode#onExit()
     */
    @Override
    public void onExit() {
        getController().getActionBar().clear();
        getController().getContent().removeAllComponents();
    }

    /**
     * This method is used to add buttons to the Project list view. Add new EDS and Export button is added.
     * 
     * @return list of buttons.
     */
    public List<Button> getLeftButtons() {
        List<Button> buttons = new ArrayList<Button>();
        if (controller.userHasSufficientRights(EdsRight.APP_PROJECT_MENU_CREATE_EDS)) {
            buttons.add(new AddNewEdsButton(getController()));
        }
        if (controller.userHasSufficientRights(EdsRight.APP_PROJECT_MENU_EXPORT_TAB_VIEW)) {
            buttons.add(new ExportButton(this, controller));
            buttons.add(new ExportFCButton(this, controller));
        }
        return buttons;
    }

    /**
     * This method is used to add buttons to the right of the view. No button is added in this method.
     * 
     * @return list of buttons.
     */
    public List<Button> getRightButtons() {
        return Collections.EMPTY_LIST;
    }

    /**
     * This method is used to retrieve the Projects view.
     * 
     * @return Component for Project list view.
     */
    public Layout getView() {
        if (projectView == null) {
            projectView = new ProjectView(getController(), project);
        }
        projectView.refresh();
        return projectView;
    }

    /**
     * This method is used to export the list of EDS in XML format.
     * 
     * @see com.inetpsa.eds.application.actionbar.exportxml.I_XmlExportable#exportXml()
     */
    public void exportXml() {

        List<EdsEds> edsEdses = EDSdao.getInstance().getAllEDSByProject(project);

        boolean export = true;
        for (EdsEds eds : edsEdses) {

            try {
                EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), eds.getEdsId());
            } catch (E_AccessLocked ex) {
                export = false;
                getController().showError(
                        controller.getBundle().getString("form-fiche-verouille"),
                        MessageFormat.format(controller.getBundle().getString("form-fiche-project-verouille-message"), eds.getEdsName(), ex.getUser()
                                .toPSAIdentity()));
            }
        }
        //

        if (export) {
            WsTestWindow window = new WsTestWindow(controller, WsTestWindow.EXPORT_PROJECT, project.getPName());
            window.setModal(true);
            controller.getApplication().getMainWindow().addWindow(window);
            window.center();
        }

    }

    // PROTECTED
    // PRIVATE
    /**
     * This variable stores the project details.
     */
    private EdsProject project;
    /**
     * This variable stores the project view.
     */
    private ProjectView projectView;

    /**
     * Initialization method.
     */
    private void init() {
    }

    @Override
    public void showExport() {

        final Window subWindow = new Window("Export");
        Label label = new Label("Export type : ");
        Button buttonXml = new Button("XML");
        Button buttonExcel = new Button("Excel");

        buttonXml.addListener(new ClickListener() {

            private static final long serialVersionUID = 4316184801919825007L;

            @Override
            public void buttonClick(ClickEvent event) {
                exportXml();
                subWindow.getParent().removeWindow(subWindow);
            }
        });

        buttonExcel.addListener(new ClickListener() {

            private static final long serialVersionUID = -8998467674091400655L;

            @Override
            public void buttonClick(ClickEvent event) {
                subWindow.getParent().removeWindow(subWindow);

                List<EdsEds> edsEdses = EDSdao.getInstance().getAllEDSByProject(project);

                try {
                    // Lock the EDS dao
                    for (EdsEds eds : edsEdses) {
                        EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), eds.getEdsId());
                    }

                    // File template
                    File template = new File(controller.getApplication().getContext().getBaseDirectory().getAbsolutePath()
                            + "/VAADIN/templates/template.xlsm");

                    if (!template.exists()) {
                        getController().showError("Export error", "Impossible de trouver le fichier de template");
                        return;
                    }

                    final ByteArrayOutputStream dest = new ByteArrayOutputStream();

                    // Export
                    new ExportExcelEDS(template, dest, controller, edsEdses).write();

                    // Response stream
                    Format formatter = new SimpleDateFormat(" dd-MM-yyyy HH-mm-ss");
                    StreamResource sr = new StreamResource(new StreamSource() {

                        private static final long serialVersionUID = 5070826190728599138L;

                        @Override
                        public InputStream getStream() {
                            return new ByteArrayInputStream(dest.toByteArray());
                        }
                    }, project.getPName() + formatter.format(new Date()) + ".xlsm", controller.getApplication());

                    sr.setCacheTime(5000);
                    sr.setMIMEType("application/octet-stream");
                    getController().getApplication().getMainWindow().open(sr, "_top");

                } catch (IOException e) {
                    getController().showError("Export error", "Erreur d'écriture à la génération du fichier");
                    e.printStackTrace();
                } catch (ExcelExportException e) {
                    getController().showError("Export error", "Erreur d'export à la génération du fichier");
                    e.printStackTrace();
                } catch (E_AccessLocked e1) {
                    getController().showError("Export error", "Erreur d'accès à la ressource EDS à la génération du fichier");
                    e1.printStackTrace();
                } catch (InvalidFormatException e) {
                    getController().showError("Export error", "Format du template invalide");
                    e.printStackTrace();
                } finally {
                    for (EdsEds eds : edsEdses) {
                        EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), eds.getEdsId());
                    }
                }
            }
        });

        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(buttonXml);
        layout.addComponent(buttonExcel);

        subWindow.addComponent(label);
        subWindow.addComponent(layout);

        subWindow.center();
        subWindow.setModal(true);
        controller.getApplication().getMainWindow().addWindow(subWindow);
    }

    @Override
    public void showExportFC() {
        List<EdsEds> edsEdses = EDSdao.getInstance().getAllEDSByProject(project);
        try {
            for (EdsEds eds : edsEdses) {
                EDSdao.getInstance().lockEdsAccess(getController().getAuthenticatedUser(), eds.getEdsId());
            }
            // File template
            File template = new File(EN_FormNode.class.getClassLoader().getResource("/templates/template_fc.xlsm").getFile());

            if (!template.exists()) {
                getController().showError("Export error", "Impossible de trouver le fichier de template");
                return;
            }

            final ByteArrayOutputStream dest = new ByteArrayOutputStream();

            // Export
            new ExportExcelCHS(template, dest, controller, edsEdses).write();

            // Response stream
            Format formatter = new SimpleDateFormat(" dd-MM-yyyy HH-mm-ss");
            StreamResource sr = new StreamResource(new StreamResource.StreamSource() {

                private static final long serialVersionUID = 5070826190728599139L;

                @Override
                public InputStream getStream() {
                    return new ByteArrayInputStream(dest.toByteArray());
                }
            }, project.getPName() + formatter.format(new Date()) + ".xlsm", controller.getApplication());

            sr.setCacheTime(5000);
            sr.setMIMEType("application/octet-stream");
            getController().getApplication().getMainWindow().open(sr, "_top");

        } catch (IOException e) {
            getController().showError("Export error", "Erreur d'écriture à la génération du fichier");
            e.printStackTrace();
        } catch (ExcelExportException e) {
            getController().showError("Export error", "Erreur d'export à la génération du fichier");
            e.printStackTrace();
        } catch (E_AccessLocked e1) {
            getController().showError("Export error", "Erreur d'accès à la ressource EDS à la génération du fichier");
            e1.printStackTrace();
        } catch (InvalidFormatException e) {
            getController().showError("Export error", "Format du template invalide");
            e.printStackTrace();
        } finally {
            for (EdsEds eds : edsEdses) {
                EDSdao.getInstance().freeEdsAccess(controller.getAuthenticatedUser(), eds.getEdsId());
            }
        }

    }
}
