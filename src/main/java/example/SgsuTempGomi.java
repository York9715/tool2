package example;

/*
 * 
 Varchar -> getString(columnName): String
 Char ->  getString(columnName): String
 Integer  -> getInt(columnName): int
 Float  -> getFloat(columnName): float
 Boolean -> getBoolean(columnName): boolean
 Date -> getDate(columnType): Date

 public static void alternateViewTable(Connection con)
 throws SQLException {

 Statement stmt = null;
 String query =
 "select COF_NAME, SUP_ID, PRICE, " +
 "SALES, TOTAL from COFFEES";

 try {
 stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);
 while (rs.next()) {
 String coffeeName = rs.getString(1);
 int supplierID = rs.getInt(2);
 float price = rs.getFloat(3);
 int sales = rs.getInt(4);
 int total = rs.getInt(5);
 System.out.println(coffeeName + "\t" + supplierID +
 "\t" + price + "\t" + sales +
 "\t" + total);
 }
 } catch (SQLException e ) {
 JDBCTutorialUtilities.printSQLException(e);
 } finally {
 if (stmt != null) { stmt.close(); }
 }
 }

 */
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.*;
import java.util.*;
import java.util.Date;
import java.sql.*;
import java.math.*;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.log4j.Logger;

@Entity
public class SgsuTempGomi implements java.io.Serializable {

	final static Logger logger = Logger.getLogger(SgsuTempGomi.class);

	@Column(name = "INDEX_CD1")
	private long INDEX_CD1;
	@Column(name = "age1")
	private int age1;
	@Column(name = "Gender")
	private String name1;
	private double pay1;
	private Date birthday1;

	private byte[] image;

	private boolean INDEX_CD1SetFlg = false;
	// INDEX_CD
	private boolean age1SetFlg = false;
	private boolean name1SetFlg = false;
	private boolean pay1SetFlg = false;
	// birthday
	private boolean birthday1SetFlg = false;

	private String WHERE;
	private String Sel;
	private String Upd;
	private String Ins;
	private String Del;
	private long rcnt;
	private ArrayList<SgsuTempGomi> rsltvec_;

	private Connection conn = null;
	private Statement stmt;

	public SgsuTempGomi() {
		INDEX_CD1 = 0;
		age1 = 0;
		name1 = null;
		pay1 = 0;
		birthday1 = null;

		WHERE = "";
		Sel = "SELECT INDEX_CD as INDEX_CD1,age AS age1,name AS name1,TO_CHAR(pay) AS pay1,birthday AS birthday1 FROM SgsuTempGomi";
		Ins = "INSERT INTO SgsuTempGomi(INDEX_CD,age,name,pay,birthday) VALUES(";
		Upd = "UPDATE SgsuTempGomi SET ";
		Del = "DELETE FROM SgsuTempGomi";
		rcnt = 0;
	}

	/**
	 * SgsuTempGomi construction
	 * 
	 * @param conn
	 *            the database Connection.
	 * @return TRUE if connection is available
	 */	
	public SgsuTempGomi(Connection conn) {
		setConnection(conn);
	}

	/**
	 * Set Database Connection
	 * 
	 * @param conn
	 *            the database Connection.
	 * @return TRUE if connection is available
	 */
	public boolean setConnection(Connection conn) {
		if (conn == null)
			return false;
		this.conn = conn;
		try {
			stmt = this.conn.createStatement();
		} catch (Exception e) {
			stmt = null;
			return false;
		}
		return true;
	}

	private void execSetResult(ResultSet rs_) throws SQLException {
		rsltvec_ = new ArrayList<SgsuTempGomi>();
		try {
			while (rs_.next()) {
				SgsuTempGomi rslt_SgsuTempGomi = new SgsuTempGomi();
				rslt_SgsuTempGomi.setINDEX_CD1(rs_.getLong(1));
				rslt_SgsuTempGomi.setAge1(rs_.getInt(2));
				rslt_SgsuTempGomi.setName1(rs_.getString(3));
				rslt_SgsuTempGomi.setPay1(rs_.getFloat(4));
				rslt_SgsuTempGomi.setBirthday1(rs_.getDate(5));
				rsltvec_.add(rslt_SgsuTempGomi);
			}
			setResultCnt(rsltvec_.size());
		} catch (SQLException e) {
			throw e;
		}
	}

	private final String getInsSQL() {
		StringBuffer sql_ = new StringBuffer("INSERT INTO SgsuTempGomi(");
		StringBuffer sqlValues = new StringBuffer("");
		if (INDEX_CD1SetFlg) {
			sql_.append("INDEX_CD,");
			sqlValues.append(String.valueOf(INDEX_CD1) + ",");
		}

		if (age1SetFlg) {
			sql_.append("age,");
			sqlValues.append(String.valueOf(age1) + ",");
		}

		if (name1SetFlg) {
			sql_.append("name,");
			sqlValues.append("'" + name1 + "',");
		}

		if (birthday1SetFlg) {
			sql_.append("birthday,");
			sqlValues.append("'" + birthday1 + "',");
		}

		String myIns = sql_.toString();
		if ((myIns.substring(myIns.length() - 1, myIns.length() - 0))
				.equals(",")) {
			myIns = myIns.substring(0, myIns.length() - 1);
		}

		String myValue = sqlValues.toString();
		if ((myValue.substring(myValue.length() - 1, myValue.length() - 0))
				.equals(",")) {
			myValue = myValue.substring(0, myValue.length() - 1);
		}

		return myIns + " ) valuies(" + myValue + ")";
	}

	private final String getUpdSQL() {
		StringBuffer sql_ = new StringBuffer(Upd);
		if (INDEX_CD1SetFlg) {
			sql_.append("INDEX_CD = " + getINDEX_CD1() + ",");
		}
		if (age1SetFlg) {
			sql_.append("age = " + getAge1() + ",");
		}
		if (name1SetFlg) {
			if (getName1() == null) {
				sql_.append("name = null,");
			} else {
				sql_.append("name = '" + getName1() + "',");
			}
		}
		if (pay1SetFlg) {
			sql_.append("pay = " + getPay1() + ",");
		}
		if (birthday1SetFlg) {
			if (getBirthday1() == null) {
				sql_.append("birthday = null ");
			} else {
				sql_.append("birthday = '" + getBirthday1() + "'");
			}
		}

		String mysql = sql_.toString();
		if ((mysql.substring(sql_.length() - 1, sql_.length() - 0)).equals(",")) {
			mysql = mysql.substring(0, sql_.length() - 1);
		}

		return mysql + getWhere();
	}

	private final void setWhere(String where_) {
		if (where_ == null) {
			WHERE = "";
		} else {
			WHERE = " WHERE " + where_;
		}
	}

	public final String getWhere() {
		return WHERE;
	}

	private final String getSelSQL() {
		return Sel + getWhere();
	}

	private final String getDelSQL() {
		return Del + getWhere();
	}

	public void setUpdateFlg() {
		// INDEX_CD
		INDEX_CD1SetFlg = false;
		// age
		age1SetFlg = false;
		name1SetFlg = false;
		pay1SetFlg = false;
		birthday1SetFlg = false;
	}

	public final List<SgsuTempGomi> exeSelect(String where_) throws SQLException {
		where_ = where_.trim();
		if (where_.equals(""))
			where_ = " 0 = 0";

		if ((conn == null) || (stmt == null))
			return null;
		ResultSet rs1;
		setWhere(where_);

		logger.info("setWhere(where_)=" + where_);
		logger.info("getWhere(where_)=" + getWhere());

		// exec select statement
		String mystr = getSelSQL();
		logger.info("getSelSQL()=" + getSelSQL());
		try {
			rs1 = stmt.executeQuery(getSelSQL());
		} catch (Exception e) {
			System.out.println("exeSelect1:" + e.getMessage());
			return null;
		}
		try {
			execSetResult(rs1);
			if (rs1 != null) {
				rs1.close();
			}
		} catch (Exception e) {
			logger.error(e);
			logger.error("exeSelect2:" + e.getMessage());
			throw e;
		}
		
		return rsltvec_;
	}

	public final boolean execDelete(String where_) throws SQLException {
		if ((conn == null) || (stmt == null))
			return false;
		if (where_.equals(""))
			where_ = " 0 = 0 ";
		setWhere(where_);

		// process delete
		String mysql = getDelSQL();
		logger.info("mysql :" + mysql);
		try {
			stmt.executeUpdate(mysql);
		} catch (Exception e) {
			logger.info("execDelete :" + e.getMessage());
			logger.error(e);
			throw e;
		}
		return true;
	}

	public final boolean execUpdate(String where_) throws SQLException {
		if ((conn == null) || (stmt == null))
			return false;

		StringBuffer sql = new StringBuffer("");
		setWhere(where_);
		logger.info("execUpdate:sql=" + getUpdSQL());
		// process update
		try {
			stmt.executeUpdate(getUpdSQL());
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
		return true;
	}

	// process insert
	public final boolean execInsert() throws SQLException {
		if ((conn == null) || (stmt == null))
			return false;
		try {
			stmt.executeUpdate(getInsSQL());
		} catch (SQLException e) {
			logger.info(e);
			throw e;
		}
		return true;
	}

	public final ArrayList<SgsuTempGomi> getResult(int stcnt_, int endcnt_) {
		ArrayList<SgsuTempGomi> rtn_ = new ArrayList<SgsuTempGomi>();
		int i_;

		for (i_ = stcnt_; i_ < endcnt_ + 1; i_++) {
			rtn_.add(rsltvec_.get(i_));
		}
		return rtn_;
	}

	private final void setResultCnt(long rcnt_) {
		rcnt = rcnt_;
	}

	/**
	 * Return INDEX_CD1 property.
	 *
	 * @return INDEX_CD1 property
	 */
	public final long getINDEX_CD1() {
		return INDEX_CD1;
	}

	/**
	 * Set INDEX_CD1 property.
	 *
	 * @param INDEX_CD1
	 *            Text .
	 */
	public void setINDEX_CD1(long iNDEX_CD1) {
		INDEX_CD1 = iNDEX_CD1;
	}

	public int getAge1() {
		return age1;
	}

	public void setAge1(int age1) {
		this.age1SetFlg = true;
		this.age1 = age1;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1SetFlg = true;
		this.name1 = name1;
	}

	public double getPay1() {
		return pay1;
	}

	public void setPay1(double pay1) {
		this.pay1SetFlg = true;
		this.pay1 = pay1;
	}

	public Date getBirthday1() {
		return birthday1;
	}

	public void setBirthday1(Date birthday1) {
		this.birthday1SetFlg = true;
		this.birthday1 = birthday1;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		// this.imageSetFlg=true;
		this.image = image;
	}

	@Override
	public String toString() {
		return "INDEX_CD1: " + INDEX_CD1 + "\n" + "age1: " + age1 + "\n"
				+ "name1: " + name1 + "\n" + "birthday1: " + birthday1 + "\n";
	}
}
