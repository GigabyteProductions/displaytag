package org.displaytag.sample;

import java.util.Random;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * A test class that has data that looks more like information that comes back in a report...
 * @author epesh
 * @version $Revision$ ($Author$)
 */
public class ReportableListObject extends Object implements Comparable
{
    /**
     * random number producer
     */
    private static Random mRandom = new Random();

    /**
     * city
     */
    private String city;

    /**
     * project
     */
    private String project;

    /**
     * task
     */
    private String task;

    /**
     * amount
     */
    private double amount;

    /**
     * city names
     */
    private static String[] mCities = { "Roma", "Olympia", "Neapolis", "Carthago" };

    /**
     * project names
     */
    private static String[] mProjects = { "Taxes", "Arts", "Army", "Gladiators" };

    /**
     * Constructor for ReportableListObject
     */
    public ReportableListObject()
    {
        this.amount = (mRandom.nextInt(99999) + 1) / 100;
        this.city = mCities[mRandom.nextInt(mCities.length)];
        this.project = mProjects[mRandom.nextInt(mProjects.length)];
        this.task = RandomSampleUtil.getRandomSentence(4);
    }

    /**
     * getter for city
     * @return String city
     */
    public String getCity()
    {
        return this.city;
    }

    /**
     * getter for project
     * @return String project
     */
    public String getProject()
    {
        return this.project;
    }

    /**
     * getter for task
     * @return String task
     */
    public String getTask()
    {
        return this.task;
    }

    /**
     * getter for amount
     * @return double amount
     */
    public double getAmount()
    {
        return this.amount;
    }

    /**
     * @see java.lang.Comparable#compareTo(Object)
     */
    public int compareTo(Object object)
    {
        ReportableListObject myClass = (ReportableListObject) object;
        return new CompareToBuilder()
            .append(this.project, myClass.project)
            .append(this.amount, myClass.amount)
            .append(this.city, myClass.city)
            .append(this.task, myClass.task)
            .toComparison();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE)
            .append("project", this.project)
            .append("amount", this.amount)
            .append("city", this.city)
            .append("task", this.task)
            .toString();
    }

}
