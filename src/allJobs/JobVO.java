/**
 * 
 */
package allJobs;

/**
 * @author pattnair
 *
 */
public class JobVO {
	
	
	private String jobName;
	private String buisnessDate;
	/**
	 * @return the jobName
	 */
	public String getJobName() {
		return jobName;
	}
	/**
	 * @param jobName the jobName to set
	 */
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	/**
	 * @return the buisnessDate
	 */
	public String getBuisnessDate() {
		return buisnessDate;
	}
	/**
	 * @param buisnessDate the buisnessDate to set
	 */
	public void setBuisnessDate(String buisnessDate) {
		this.buisnessDate = buisnessDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "JobVO [jobName=" + jobName + ", buisnessDate=" + buisnessDate
				+ "]";
	}

}
