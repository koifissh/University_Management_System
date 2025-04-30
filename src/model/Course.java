package model;

public class Course {

	// fields
	private String code;
	private String name;
	private String description;
	private int max_capacity;
	private String status;

	// constructors
	public Course() {
	};

	public Course(String code, String name, String description, int capacity, String status) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.max_capacity = capacity;
		this.status = status;
	}

	// setters and getters
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the maxCapacity
	 */
	public int getCapacity() {
		return max_capacity;
	}

	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setCapacity(int capacity) {
		this.max_capacity = capacity;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Course{" + "code='" + code + '\'' + ", name='" + name + '\'' + ", description='" + description + '\''
				+ ", maxCapacity=" + max_capacity + ", status='" + status + '\'' + '}';
	}

}
