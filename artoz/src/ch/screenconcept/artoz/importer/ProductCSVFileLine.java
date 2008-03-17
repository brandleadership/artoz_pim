package ch.screenconcept.artoz.importer;

import ch.screenconcept.artoz.exceptions.CSVFormatException;

public class ProductCSVFileLine extends AbstractCSVFileLine
{

	public ProductCSVFileLine(String[] line) throws CSVFormatException
	{
		super(line);

		if (getCode() == null)
			throw new CSVFormatException("Code is empty");
		if (getPlchf01() != null)
			if (getPlCHFUnit() == null)
				throw new CSVFormatException("PlCHFUnit is empty and a CHF price is set");
		if (getPleur01() != null)
			if (getPlEURUnit() == null)
				throw new CSVFormatException("PlEURUnit is empty and a EUR price is set");
		if (getPlgbp01() != null)
			if (getPlGBPUnit() == null)
				throw new CSVFormatException("PlGBPUnit is empty and a GBP price is set");
	}

	public String getCode()
	{
		return getColumn(0);
	}

	public String getCategory()
	{
		return getColumn(1);
	}

	public String getCategoryName()
	{
		return getColumn(2);
	}

	public String getDIN()
	{
		return getColumn(3);
	}

	public String getDimensions()
	{
		return getColumn(4);
	}

	public String getGrammage()
	{
		return getColumn(5);
	}

	public String getEAN()
	{
		return getColumn(6);
	}

	public String getStatus()
	{
		return getColumn(7);
	}

	public Double getSalesUnit()
	{
		return getDouble(8);
	}

	public String getItemTypeGroup()
	{
		return getColumn(9);
	}

	public Integer getMaterialGroup()
	{
		return getInteger(10);
	}

	public Integer getNewnessCode()
	{
		return getInteger(11);
	}

	public boolean getMdaView()
	{
		if (getColumn(16) != null)
			return true;
		return false;
	}

	public String getShortTextDE()
	{
		return getColumn(17);
	}

	public String getShortTextEN()
	{
		return getColumn(18);
	}

	public String getShortTextFR()
	{
		return getColumn(19);
	}

	public String getShortTextIT()
	{
		return getColumn(20);
	}

	public String getShortTextPT()
	{
		return getColumn(21);
	}

	public String getShortTextES()
	{
		return getColumn(22);
	}

	public Integer getPlCHFUnit()
	{
		return getInteger(23);
	}

	public Long getPlstchf01()
	{
		return getLong(24);
	}

	public Double getPlchf01()
	{
		return getDouble(25);
	}

	public Long getPlstchf02()
	{
		return getLong(26);
	}

	public Double getPlchf02()
	{
		return getDouble(27);
	}

	public Long getPlstchf03()
	{
		return getLong(28);
	}

	public Double getPlchf03()
	{
		return getDouble(29);
	}

	public Long getPlstchf04()
	{
		return getLong(30);
	}

	public Double getPlchf04()
	{
		return getDouble(31);
	}

	public Long getPlstchf05()
	{
		return getLong(32);
	}

	public Double getPlchf05()
	{
		return getDouble(33);
	}

	public Long getPlstchf06()
	{
		return getLong(34);
	}

	public Double getPlchf06()
	{
		return getDouble(35);
	}

	public Long getPlstchf07()
	{
		return getLong(36);
	}

	public Double getPlchf07()
	{
		return getDouble(37);
	}

	public Long getPlstchf08()
	{
		return getLong(38);
	}

	public Double getPlchf08()
	{
		return getDouble(39);
	}

	public Long getPlstchf09()
	{
		return getLong(40);
	}

	public Double getPlchf09()
	{
		return getDouble(41);
	}

	public Long getPlstchf10()
	{
		return getLong(42);
	}

	public Double getPlchf10()
	{
		return getDouble(43);
	}

	public Integer getPlEURUnit()
	{
		return getInteger(44);
	}

	public Long getPlsteur01()
	{
		return getLong(45);
	}

	public Double getPleur01()
	{
		return getDouble(46);
	}

	public Long getPlsteur02()
	{
		return getLong(47);
	}

	public Double getPleur02()
	{
		return getDouble(48);
	}

	public Long getPlsteur03()
	{
		return getLong(49);
	}

	public Double getPleur03()
	{
		return getDouble(50);
	}

	public Long getPlsteur04()
	{
		return getLong(51);
	}

	public Double getPleur04()
	{
		return getDouble(52);
	}

	public Long getPlsteur05()
	{
		return getLong(53);
	}

	public Double getPleur05()
	{
		return getDouble(54);
	}

	public Long getPlsteur06()
	{
		return getLong(55);
	}

	public Double getPleur06()
	{
		return getDouble(56);
	}

	public Long getPlsteur07()
	{
		return getLong(57);
	}

	public Double getPleur07()
	{
		return getDouble(58);
	}

	public Long getPlsteur08()
	{
		return getLong(59);
	}

	public Double getPleur08()
	{
		return getDouble(60);
	}

	public Long getPlsteur09()
	{
		return getLong(61);
	}

	public Double getPleur09()
	{
		return getDouble(62);
	}

	public Long getPlsteur10()
	{
		return getLong(63);
	}

	public Double getPleur10()
	{
		return getDouble(64);
	}

	public Integer getPlGBPUnit()
	{
		return getInteger(65);
	}

	public Long getPlstgbp01()
	{
		return getLong(66);
	}

	public Double getPlgbp01()
	{
		return getDouble(67);
	}

	public Long getPlstgbp02()
	{
		return getLong(68);
	}

	public Double getPlgbp02()
	{
		return getDouble(69);
	}

	public Long getPlstgbp03()
	{
		return getLong(70);
	}

	public Double getPlgbp03()
	{
		return getDouble(71);
	}

	public Long getPlstgbp04()
	{
		return getLong(72);
	}

	public Double getPlgbp04()
	{
		return getDouble(73);
	}

	public Long getPlstgbp05()
	{
		return getLong(74);
	}

	public Double getPlgbp05()
	{
		return getDouble(75);
	}

	public Long getPlstgbp06()
	{
		return getLong(76);
	}

	public Double getPlgbp06()
	{
		return getDouble(77);
	}

	public Long getPlstgbp07()
	{
		return getLong(78);
	}

	public Double getPlgbp07()
	{
		return getDouble(79);
	}

	public Long getPlstgbp08()
	{
		return getLong(80);
	}

	public Double getPlgbp08()
	{
		return getDouble(81);
	}

	public Long getPlstgbp09()
	{
		return getLong(82);
	}

	public Double getPlgbp09()
	{
		return getDouble(83);
	}

	public Long getPlstgbp10()
	{
		return getLong(84);
	}

	public Double getPlgbp10()
	{
		return getDouble(85);
	}
}
