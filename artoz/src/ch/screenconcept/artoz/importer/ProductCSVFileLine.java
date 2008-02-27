package ch.screenconcept.artoz.importer;

public class ProductCSVFileLine extends AbstractCSVFileLine
{

	public ProductCSVFileLine(String[] line)
	{
		super(line);
	}

	public String getCode()
	{
		return getColumn(0);
	}

	public String getWarengruppe()
	{
		return getColumn(1);
	}

	public String getDIN()
	{
		return getColumn(2);
	}

	public String getDimensions()
	{
		return getColumn(3);
	}

	public String getGrammage()
	{
		return getColumn(4);
	}

	public String getEAN()
	{
		return getColumn(5);
	}

	public String getEANType()
	{
		return getColumn(6);
	}

	public String getStatus()
	{
		return getColumn(7);
	}

	public Integer getSalesUnit()
	{
		return getInteger(8);
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

	public Integer getMdaView()
	{
		return getInteger(16);
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
	
	public Long getPlstchf01(){
		return getLong(23);
	}
	
	public Double getPlchf01(){
		return getDouble(24);
	}
	
	public Long getPlstchf02(){
		return getLong(25);
	}
	
	public Double getPlchf02(){
		return getDouble(26);
	}
	
	public Long getPlstchf03(){
		return getLong(27);
	}
	
	public Double getPlchf03(){
		return getDouble(28);
	}
	
	public Long getPlstchf04(){
		return getLong(29);
	}
	
	public Double getPlchf04(){
		return getDouble(30);
	}
	
	public Long getPlstchf05(){
		return getLong(31);
	}
	
	public Double getPlchf05(){
		return getDouble(32);
	}
	
	public Long getPlstchf06(){
		return getLong(3);
	}
	
	public Double getPlchf06(){
		return getDouble(34);
	}
	
	public Long getPlstchf07(){
		return getLong(35);
	}
	
	public Double getPlchf07(){
		return getDouble(36);
	}	
	
	public Long getPlstchf08(){
		return getLong(37);
	}
	
	public Double getPlchf08(){
		return getDouble(38);
	}
	
	public Long getPlstchf09(){
		return getLong(39);
	}
	
	public Double getPlchf09(){
		return getDouble(40);
	}
	
	public Long getPlstchf10(){
		return getLong(41);
	}
	
	public Double getPlchf10(){
		return getDouble(42);
	}

	public Long getPlsteur01(){
		return getLong(43);
	}
	
	public Double getPleur01(){
		return getDouble(44);
	}

	public Long getPlsteur02(){
		return getLong(45);
	}
	
	public Double getPleur02(){
		return getDouble(46);
	}

	public Long getPlsteur03(){
		return getLong(47);
	}
	
	public Double getPleur03(){
		return getDouble(48);
	}

	public Long getPlsteur04(){
		return getLong(49);
	}
	
	public Double getPleur04(){
		return getDouble(50);
	}

	public Long getPlsteur05(){
		return getLong(51);
	}
	
	public Double getPleur05(){
		return getDouble(52);
	}

	public Long getPlsteur06(){
		return getLong(53);
	}
	
	public Double getPleur06(){
		return getDouble(54);
	}

	public Long getPlsteur07(){
		return getLong(55);
	}
	
	public Double getPleur07(){
		return getDouble(56);
	}

	public Long getPlsteur08(){
		return getLong(57);
	}
	
	public Double getPleur08(){
		return getDouble(58);
	}

	public Long getPlsteur09(){
		return getLong(59);
	}
	
	public Double getPleur09(){
		return getDouble(60);
	}

	public Long getPlsteur10(){
		return getLong(61);
	}
	
	public Double getPleur10(){
		return getDouble(62);
	}

	public Long getPlstgbp01(){
		return getLong(63);
	}
	
	public Double getPlgbp01(){
		return getDouble(64);
	}
	
	public Long getPlstgbp02(){
		return getLong(65);
	}
	
	public Double getPlgbp02(){
		return getDouble(65);
	}
	
	public Long getPlstgbp03(){
		return getLong(67);
	}
	
	public Double getPlgbp03(){
		return getDouble(68);
	}
	
	public Long getPlstgbp04(){
		return getLong(69);
	}
	
	public Double getPlgbp04(){
		return getDouble(70);
	}
	
	public Long getPlstgbp05(){
		return getLong(71);
	}
	
	public Double getPlgbp05(){
		return getDouble(72);
	}
	
	public Long getPlstgbp06(){
		return getLong(73);
	}
	
	public Double getPlgbp06(){
		return getDouble(74);
	}
	
	public Long getPlstgbp07(){
		return getLong(75);
	}
	
	public Double getPlgbp07(){
		return getDouble(76);
	}
	
	public Long getPlstgbp08(){
		return getLong(77);
	}
	
	public Double getPlgbp08(){
		return getDouble(78);
	}
	
	public Long getPlstgbp09(){
		return getLong(79);
	}
	
	public Double getPlgbp09(){
		return getDouble(80);
	}
	
	public Long getPlstgbp10(){
		return getLong(81);
	}
	
	public Double getPlgbp10(){
		return getDouble(82);
	}
}
