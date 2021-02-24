package hcd.ca.gov.hpd;

import hcd.ca.gov.hpd.util.JsfUtil;
import hcd.ca.gov.hpd.util.PaginationHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Named("requesterController")
@SessionScoped
public class RequesterController implements Serializable {

    private Requester current;
    private DataModel items = null;
    @EJB
    private hcd.ca.gov.hpd.RequesterFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;

    public RequesterController() {
    }
    
    /*   Santosh   */
    @PersistenceContext
    private EntityManager entityManager;
    private List<String> hpdJurisdictionList = new ArrayList<>();
    public List<String> getJurisdiction() {
        //if(assetSubTypeList!=null && !assetSubTypeList.isEmpty()) assetSubTypeList.clear();
        System.out.println("Selected Jurisdiction : ");       
        Query q =  entityManager.createQuery("SELECT DISTINCT h.jurisdiction FROM HousingElements h ",String.class);
        
        hpdJurisdictionList = q.getResultList();
        return hpdJurisdictionList;
    }
    private List<String> hpdcountry = new ArrayList<>();
    public List<String> getCountry() {
        Query q =  entityManager.createQuery("SELECT DISTINCT h.county FROM HousingElements h ",String.class);
        hpdcountry = q.getResultList();
        return hpdcountry;
    }
    
    private List<String> hpdDocType = new ArrayList<>();
    public List<String> getDocType() {
        Query q =  entityManager.createQuery("SELECT DISTINCT h.documentType FROM HousingElements h ",String.class);
        hpdDocType = q.getResultList();
        return hpdDocType;
    }
    private List<String> hpdPlanningPeriod = new ArrayList<>();
    public List<String> getPlanningPeriod() {
        Query q =  entityManager.createQuery("SELECT DISTINCT h.planningPeriod FROM HousingElements h ",String.class);
        hpdPlanningPeriod = q.getResultList();
        return hpdPlanningPeriod;
    }
    
    public void handleKeyEvent() {
        System.out.println("Key Up.....");
        System.out.println("PO....."+jurisdictions);
    }
    
    String jurisdictions;
    String document_types;
    String planning_period;
    public String getJurisdictions() {
        return jurisdictions;
    }

    public void setJurisdictions(String jurisdictions) {
        this.jurisdictions = jurisdictions;
    }

    public String getDocument_types() {
        return document_types;
    }

    public void setDocument_types(String document_types) {
        this.document_types = document_types;
    }
    public String getPlanning_period() {
        return planning_period;
    }

    public void setPlanning_period(String planning_period) {
        this.planning_period = planning_period;
    }
    
    public List<HousingElements> findHousingElements() {
        /*
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correct", "Correct");
	FacesContext.getCurrentInstance().addMessage(null, msg);
        */
        System.out.println("HousingElements jurisdictions: "+jurisdictions);
        List<HousingElements> assetsList = new ArrayList<>();
        //Query q =  entityManager.createQuery("SELECT p FROM HousingElements p WHERE p.jurisdiction = :jurisdictions AND p.documentType = :document_types OR p.planningPeriod = :planning_period",
        Query q =  entityManager.createQuery("SELECT p FROM HousingElements p WHERE p.jurisdiction = :jurisdictions AND p.documentType = :document_types",
          HousingElements.class);
        q.setParameter("jurisdictions", jurisdictions);
        q.setParameter("document_types", document_types);   
        //q.setParameter("planning_period", planning_period);
        assetsList = q.getResultList();        
        return assetsList;
    }
    public String resultsList() {
        
        return "results";
    }
    /* Santosh  */

    public Requester getSelected() {
        if (current == null) {
            current = new Requester();
            selectedItemIndex = -1;
        }
        return current;
    }

    private RequesterFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Requester) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        current = new Requester();
        selectedItemIndex = -1;
        return "Create";
    }

    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("RequesterCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String prepareEdit() {
        current = (Requester) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("RequesterUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Requester) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle1").getString("RequesterDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle1").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Requester getRequester(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Requester.class)
    public static class RequesterControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RequesterController controller = (RequesterController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "requesterController");
            return controller.getRequester(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Requester) {
                Requester o = (Requester) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Requester.class.getName());
            }
        }

    }

}
