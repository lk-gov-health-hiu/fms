package lk.gov.health.phsp.bean;

import lk.gov.health.phsp.entity.FuelTransaction;
import lk.gov.health.phsp.facade.FuelTransactionHistoryFacade;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.TemporalType;
import lk.gov.health.phsp.bean.util.JsfUtil;
import lk.gov.health.phsp.entity.FuelTransactionHistory;
import lk.gov.health.phsp.entity.Institution;
import lk.gov.health.phsp.entity.Vehicle;
import lk.gov.health.phsp.entity.WebUser;
import lk.gov.health.phsp.enums.FuelTransactionType;
import lk.gov.health.phsp.facade.FuelTrnasactionFacade;
import lk.gov.health.phsp.facade.InstitutionFacade;
import lk.gov.health.phsp.facade.VehicleFacade;
import lk.gov.health.phsp.facade.WebUserFacade;

@Named
@SessionScoped
public class FuelRequestAndIssueController implements Serializable {

    @EJB
    private FuelTransactionHistoryFacade fuelTransactionHistoryFacade;

    @EJB
    private FuelTrnasactionFacade fuelTransactionFacade;

    @EJB
    InstitutionFacade institutionFacade;

    @EJB
    VehicleFacade vehicleFacade;

    @EJB
    WebUserFacade webUserFacade;

    @Inject
    private WebUserController webUserController;
    @Inject
    private UserTransactionController userTransactionController;
    @Inject
    MenuController menuController;
    @Inject
    InstitutionApplicationController institutionApplicationController;
    @Inject
    VehicleApplicationController vehicleApplicationController;
    @Inject
    WebUserApplicationController webUserApplicationController;

    private List<FuelTransaction> transactions = null;
    private List<FuelTransaction> selectedTransactions = null;
    private FuelTransaction selected;

    private FuelTransactionHistory selectedTransactionHistory;
    private List<FuelTransactionHistory> selectedTransactionHistories;
    private List<FuelTransactionHistory> transactionHistories;

    private Institution institution;
    private Vehicle vehicle;
    private WebUser webUser;
    private Date fromDate;
    private Date toDate;

    public FuelRequestAndIssueController() {
    }

    public void saveSelected() {
        System.out.println("saveCurrentDocument = " + selected);
        if (selected == null) {
            return;
        }
        save(selected);
    }

    public String submitFuelRequest() {
        System.out.println("submitFuelRequest");
        System.out.println("selected = " + selected);
        if (selected == null) {
            System.out.println("Selcted is null");
            JsfUtil.addErrorMessage("Nothing selected");
            return navigateToAddVehicleFuelRequest();
        }
        System.out.println("selected.getTransactionType() = " + selected.getTransactionType());
        if (selected.getTransactionType() == null || selected.getTransactionType() != FuelTransactionType.VehicleFuelRequest) {
            System.out.println("wrong type");
            JsfUtil.addErrorMessage("Wrong selection");
            return navigateToAddVehicleFuelRequest();
        }
        save(selected);
        System.out.println("saved");
        JsfUtil.addSuccessMessage("Request Submitted");
        return navigateToViewRequest();
    }

    public void searchFuelTransaction() {

    }

    public void searchFuelRequestByVehicle() {

    }

    public String navigateToListFuelTransactions() {
        return "/issues/list";
    }

    public String navigateToAddVehicleFuelRequest() {
        selected = new FuelTransaction();
        selected.setRequestAt(new Date());
        selected.setTransactionType(FuelTransactionType.VehicleFuelRequest);
        selected.setRequestedBy(webUserController.getLoggedUser());
        selected.setRequestedInstitution(webUserController.getLoggedInstitution());
        selected.setFromInstitution(webUserController.getLoggedInstitution());
        selected.setToInstitution(webUserController.getLoggedInstitution().getSupplyInstitution());
        return "/requests/request";
    }
    
     public String navigateToAddSpecialVehicleFuelRequest() {
        selected = new FuelTransaction();
        selected.setRequestAt(new Date());
        selected.setTransactionType(FuelTransactionType.VehicleFuelRequest);
        selected.setRequestedBy(webUserController.getLoggedUser());
        selected.setRequestedInstitution(webUserController.getLoggedInstitution());
        selected.setFromInstitution(webUserController.getLoggedInstitution());
        selected.setToInstitution(webUserController.getLoggedInstitution().getSupplyInstitution());
        return "/requests/special_request";
    }

    public String navigateToIssueRequest() {
        return "/issues/issue";
    }

    public String generateRequest() {
        return "/requests/requested";
    }

    public String completeIssue() {
        return "/issues/issued";
    }

    public String navigateToListInstitutionRequests() {
        return "/requests/list";
    }

    public void listInstitutionRequests() {
        String j = "select t "
                + " from FuelTransaction t ";
//               j   += " where t.requestedInstitution=:ins ";
//             j   += " and t.requestAt between :fd and :td";
        Map m = new HashMap();
//        m.put("ins", webUserController.getLoggedInstitution());
//        m.put("fd", getFromDate());
//        m.put("td", getToDate());
        transactions = getFacade().findByJpql(j, m, TemporalType.TIMESTAMP);
    }

    public String navigateToListInstitutionIssues() {
        return "/issues/list";
    }

    public FuelTransaction getSelected() {
        return selected;
    }

    public void setSelected(FuelTransaction selected) {
        this.selected = selected;
    }

    public FuelTransaction find(Object id) {
        return fuelTransactionFacade.find(id);
    }

    private FuelTransactionHistoryFacade getFacade() {
        return fuelTransactionHistoryFacade;
    }

    public void save(FuelTransaction saving) {
        if (saving == null) {
            return;
        }
        if (saving.getId() == null) {
            fuelTransactionFacade.create(saving);
        } else {
            fuelTransactionFacade.edit(saving);
        }
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public List<FuelTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<FuelTransaction> transactions) {
        this.transactions = transactions;
    }

    public List<FuelTransaction> getSelectedTransactions() {
        return selectedTransactions;
    }

    public void setSelectedTransactions(List<FuelTransaction> selectedTransactions) {
        this.selectedTransactions = selectedTransactions;
    }

    public FuelTransactionHistory getSelectedTransactionHistory() {
        return selectedTransactionHistory;
    }

    public void setSelectedTransactionHistory(FuelTransactionHistory selectedTransactionHistory) {
        this.selectedTransactionHistory = selectedTransactionHistory;
    }

    public List<FuelTransactionHistory> getSelectedTransactionHistories() {
        return selectedTransactionHistories;
    }

    public void setSelectedTransactionHistories(List<FuelTransactionHistory> selectedTransactionHistories) {
        this.selectedTransactionHistories = selectedTransactionHistories;
    }

    public List<FuelTransactionHistory> getTransactionHistories() {
        return transactionHistories;
    }

    public void setTransactionHistories(List<FuelTransactionHistory> transactionHistories) {
        this.transactionHistories = transactionHistories;
    }

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Date getFromDate() {
        if (fromDate == null) {
            fromDate = CommonController.startOfTheMonth();
        }
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        if (toDate == null) {
            toDate = CommonController.endOfTheMonth();
        }
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    private String navigateToViewRequest() {
        return "/requests/requested";
    }

    @FacesConverter(forClass = FuelTransaction.class)
    public static class FuelTransactionConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            FuelRequestAndIssueController controller = (FuelRequestAndIssueController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "fuelRequestAndIssueController");
            return controller.find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof FuelTransaction) {
                FuelTransaction o = (FuelTransaction) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), FuelTransaction.class.getName()});
                return null;
            }
        }

    }

}
