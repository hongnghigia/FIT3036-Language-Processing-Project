package json;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Ucg {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("role")
	@Expose
	private String role;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("children")
	@Expose
	private List<Child> children = new ArrayList<Child>();

	/**
	 * 
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 
	 * @param role
	 *            The role
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return The children
	 */
	public List<Child> getChildren() {
		return children;
	}

	/**
	 * 
	 * @param children
	 *            The children
	 */
	public void setChildren(List<Child> children) {
		this.children = children;
	}

}