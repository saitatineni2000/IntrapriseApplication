package com.catapult.android.model;

public class Patient {

	private String PatientId;
	private String HealthEvalId;
	private String HealthEvalStatusId;
	private String Status;
	private String LastName;
	private String FirstName;
	private String DOB;
	private String StartTime;
	private String ElapsedTime;
	private String OutcomesProgramId;
 		
	private String Middle_Initial;	
	private String Gender ;			
	private String Email_Address 	;	
	private String Ethnicity_ID 	;	
	private String Hispanic_Latino ;		
	private String Language_ID 	;	
	private String Address_1 ;			
	private String Address_2 ;			
	private String City 	;		
	private String State ;			
	private String Postal_Code 	;	
	private String Home_Phone ;		
	private String Cell_Phone 	;	
	private String Insurance_Plan_ID ;		
	private String Insurance_Group_ID 	;
	private String Insurance_Member_ID 	;
	private String Company_Name 	;	
	private String SSN 		;	
	private String Insurance_Relation 	;
	private String Insurance_Effective_Date 	;
	private String Insurance_First_Name ;	
	private String Insurance_Last_Name 	;
	private String Employee_Id;
	
	public Patient() {
		
	}
	
	public Patient(String patientId, String healthEvalId,
			String healthEvalStatusId, String status, String lastName,
			String firstName, String dOB, String startTime, String elapsedTime,
			String outcomesProgramId) {
		super();
		PatientId = patientId;
		HealthEvalId = healthEvalId;
		HealthEvalStatusId = healthEvalStatusId;
		Status = status;
		LastName = lastName;
		FirstName = firstName;
		DOB = dOB;
		StartTime = startTime;
		ElapsedTime = elapsedTime;
		OutcomesProgramId = outcomesProgramId;
	}
	public String getPatientId() {
		return PatientId;
	}
	public void setPatientId(String patientId) {
		PatientId = patientId;
	}
	public String getHealthEvalId() {
		return HealthEvalId;
	}
	public void setHealthEvalId(String healthEvalId) {
		HealthEvalId = healthEvalId;
	}
	public String getHealthEvalStatusId() {
		return HealthEvalStatusId;
	}
	public void setHealthEvalStatusId(String healthEvalStatusId) {
		HealthEvalStatusId = healthEvalStatusId;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getElapsedTime() {
		return ElapsedTime;
	}
	public void setElapsedTime(String elapsedTime) {
		ElapsedTime = elapsedTime;
	}
	public String getOutcomesProgramId() {
		return OutcomesProgramId;
	}
	public void setOutcomesProgramId(String outcomesProgramId) {
		OutcomesProgramId = outcomesProgramId;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return Gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		Gender = gender;
	}

	/**
	 * @return the email_Address
	 */
	public String getEmail_Address() {
		return Email_Address;
	}

	/**
	 * @param email_Address the email_Address to set
	 */
	public void setEmail_Address(String email_Address) {
		Email_Address = email_Address;
	}

	/**
	 * @return the ethnicity_ID
	 */
	public String getEthnicity_ID() {
		return Ethnicity_ID;
	}

	/**
	 * @param ethnicity_ID the ethnicity_ID to set
	 */
	public void setEthnicity_ID(String ethnicity_ID) {
		Ethnicity_ID = ethnicity_ID;
	}

	/**
	 * @return the hispanic_Latino
	 */
	public String getHispanic_Latino() {
		return Hispanic_Latino;
	}

	/**
	 * @param hispanic_Latino the hispanic_Latino to set
	 */
	public void setHispanic_Latino(String hispanic_Latino) {
		Hispanic_Latino = hispanic_Latino;
	}

	/**
	 * @return the language_ID
	 */
	public String getLanguage_ID() {
		return Language_ID;
	}

	/**
	 * @param language_ID the language_ID to set
	 */
	public void setLanguage_ID(String language_ID) {
		Language_ID = language_ID;
	}

	/**
	 * @return the address_1
	 */
	public String getAddress_1() {
		return Address_1;
	}

	/**
	 * @param address_1 the address_1 to set
	 */
	public void setAddress_1(String address_1) {
		Address_1 = address_1;
	}

	/**
	 * @return the address_2
	 */
	public String getAddress_2() {
		return Address_2;
	}

	/**
	 * @param address_2 the address_2 to set
	 */
	public void setAddress_2(String address_2) {
		Address_2 = address_2;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return City;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		City = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return State;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		State = state;
	}

	/**
	 * @return the postal_Code
	 */
	public String getPostal_Code() {
		return Postal_Code;
	}

	/**
	 * @param postal_Code the postal_Code to set
	 */
	public void setPostal_Code(String postal_Code) {
		Postal_Code = postal_Code;
	}

	/**
	 * @return the home_Phone
	 */
	public String getHome_Phone() {
		return Home_Phone;
	}

	/**
	 * @param home_Phone the home_Phone to set
	 */
	public void setHome_Phone(String home_Phone) {
		Home_Phone = home_Phone;
	}

	/**
	 * @return the cell_Phone
	 */
	public String getCell_Phone() {
		return Cell_Phone;
	}

	/**
	 * @param cell_Phone the cell_Phone to set
	 */
	public void setCell_Phone(String cell_Phone) {
		Cell_Phone = cell_Phone;
	}

	/**
	 * @return the insurance_Plan_ID
	 */
	public String getInsurance_Plan_ID() {
		return Insurance_Plan_ID;
	}

	/**
	 * @param insurance_Plan_ID the insurance_Plan_ID to set
	 */
	public void setInsurance_Plan_ID(String insurance_Plan_ID) {
		Insurance_Plan_ID = insurance_Plan_ID;
	}

	/**
	 * @return the insurance_Group_ID
	 */
	public String getInsurance_Group_ID() {
		return Insurance_Group_ID;
	}

	/**
	 * @param insurance_Group_ID the insurance_Group_ID to set
	 */
	public void setInsurance_Group_ID(String insurance_Group_ID) {
		Insurance_Group_ID = insurance_Group_ID;
	}

	/**
	 * @return the insurance_Member_ID
	 */
	public String getInsurance_Member_ID() {
		return Insurance_Member_ID;
	}

	/**
	 * @param insurance_Member_ID the insurance_Member_ID to set
	 */
	public void setInsurance_Member_ID(String insurance_Member_ID) {
		Insurance_Member_ID = insurance_Member_ID;
	}

	/**
	 * @return the company_Name
	 */
	public String getCompany_Name() {
		return Company_Name;
	}

	/**
	 * @param company_Name the company_Name to set
	 */
	public void setCompany_Name(String company_Name) {
		Company_Name = company_Name;
	}

	/**
	 * @return the sSN
	 */
	public String getSSN() {
		return SSN;
	}

	/**
	 * @param sSN the sSN to set
	 */
	public void setSSN(String sSN) {
		SSN = sSN;
	}

	/**
	 * @return the insurance_Relation
	 */
	public String getInsurance_Relation() {
		return Insurance_Relation;
	}

	/**
	 * @param insurance_Relation the insurance_Relation to set
	 */
	public void setInsurance_Relation(String insurance_Relation) {
		Insurance_Relation = insurance_Relation;
	}

	/**
	 * @return the insurance_Effective_Date
	 */
	public String getInsurance_Effective_Date() {
		return Insurance_Effective_Date;
	}

	/**
	 * @param insurance_Effective_Date the insurance_Effective_Date to set
	 */
	public void setInsurance_Effective_Date(String insurance_Effective_Date) {
		Insurance_Effective_Date = insurance_Effective_Date;
	}

	/**
	 * @return the insurance_First_Name
	 */
	public String getInsurance_First_Name() {
		return Insurance_First_Name;
	}

	/**
	 * @param insurance_First_Name the insurance_First_Name to set
	 */
	public void setInsurance_First_Name(String insurance_First_Name) {
		Insurance_First_Name = insurance_First_Name;
	}

	/**
	 * @return the insurance_Last_Name
	 */
	public String getInsurance_Last_Name() {
		return Insurance_Last_Name;
	}

	/**
	 * @param insurance_Last_Name the insurance_Last_Name to set
	 */
	public void setInsurance_Last_Name(String insurance_Last_Name) {
		Insurance_Last_Name = insurance_Last_Name;
	}

	/**
	 * @return the employee_Id
	 */
	public String getEmployee_Id() {
		return Employee_Id;
	}

	/**
	 * @param employee_Id the employee_Id to set
	 */
	public void setEmployee_Id(String employee_Id) {
		Employee_Id = employee_Id;
	}

	/**
	 * @return the middle_Initial
	 */
	public String getMiddle_Initial() {
		return Middle_Initial;
	}

	/**
	 * @param middle_Initial the middle_Initial to set
	 */
	public void setMiddle_Initial(String middle_Initial) {
		Middle_Initial = middle_Initial;
	}
	
	

}
