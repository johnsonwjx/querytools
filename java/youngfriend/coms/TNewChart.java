package youngfriend.coms;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Map;

import javax.swing.BorderFactory;

import org.dom4j.Element;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import youngfriend.beans.PropDto;
import youngfriend.common.util.StringUtils;
import youngfriend.utils.ComEum;

public class TNewChart extends ChartPanel implements IStyleCom {
	private static final long serialVersionUID = 1L;
	private DefaultCom defaultCom = null;

	public TNewChart() {
		super(null);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		defaultCom = new DefaultCom(this);
		defaultCom.addUIProp(Arrays.asList("chartType"));
		this.setLayout(new BorderLayout());
	}

	@Override
	public void init(Element propE) {
		defaultCom.init(propE);
	}

	@Override
	public void upateUIByProps() {
		int Width = 0, Height = 0, Left = 0, Top = 0;
		String temp = this.getPropValue("Width");
		if (StringUtils.isNumberString(temp)) {
			Width = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Height");
		if (StringUtils.isNumberString(temp)) {
			Height = Integer.parseInt(temp);
		}
		temp = this.getPropValue("Left");
		if (StringUtils.isNumberString(temp)) {
			Left = Integer.parseInt(temp);
		}

		temp = this.getPropValue("Top");
		if (StringUtils.isNumberString(temp)) {
			Top = Integer.parseInt(temp);
		}
		this.setBounds(Left, Top, Width, Height);
		String type = getPropValue("chartType");
		// ����jfreechart����
		JFreeChart jfreechart = null;
		if ("14".equals(type))// ��״ͼ
		{
			jfreechart = createPieChart(createPieDataset());
		} else if ("11".equals(type))// ���ͼ
		{
			jfreechart = createGanttChart(createCategoryDataset());
		} else if ("9".equals(type))// ��״ͼ
		{
			jfreechart = createChart(createCategoryDataset());
		} else if ("3".equals(type))// ��ͼ
		{
			jfreechart = createXYChart(createXYDataset());
		}
		this.setChart(jfreechart);
		this.updateUI();
	}

	private CategoryDataset createCategoryDataset() {
		String s = "First";
		String s1 = "Second";
		String s2 = "��״ͼ";
		// ����defaultcategorydataset����Դ����

		DefaultCategoryDataset defaultcategorydataset = new DefaultCategoryDataset();
		// ���defaultcategorydataset����Դ�����������

		defaultcategorydataset.addValue(1.0D, s, s2);
		defaultcategorydataset.addValue(5D, s1, s2);
		return defaultcategorydataset;
	}

	private PieDataset createPieDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("One", new Double(43.2));
		dataset.setValue("Two", new Double(10.0));
		dataset.setValue("Three", new Double(27.5));
		dataset.setValue("Four", new Double(17.5));
		dataset.setValue("Five", new Double(11.0));
		dataset.setValue("Six", new Double(19.4));
		return dataset;
	}

	private JFreeChart createChart(CategoryDataset categorydataset) {
		JFreeChart jfreechart = ChartFactory.createBarChart("��״ͼ", "������", "������", categorydataset/*
																								 * ����Դ
																								 */, PlotOrientation.VERTICAL/*
																															 * ����
																															 */, true, true, false);
		jfreechart.setBackgroundPaint(new Color(0xbbbbdd));// ���ñ���ɫ
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		// �õ�ͼ���Ա㾫ϸ����
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// ȡ�ø���ͼ�εķ�Χ������,ָ������
		numberaxis.setTickLabelFont(new Font("����", Font.ITALIC, 18));
		// ���������������,��񣬴�С
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// �����������Ա�׼����Ϊ��λ
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		// barrenderer��ʾ�õ����е����μ��϶���
		barrenderer.setDrawBarOutline(false);// ����ʾ���ε���߿�
		barrenderer.setMaximumBarWidth(2D);// ����ÿ��ͼ�ε������
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, Color.black);// ���ϵ��½������ɫ
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, Color.red);// ���ϵ��½������ɫ
		barrenderer.setSeriesPaint(0, gradientpaint);// ��һ������
		barrenderer.setSeriesPaint(1, gradientpaint1);// �ڶ�������

		return jfreechart;
	}

	private static JFreeChart createPieChart(PieDataset categorydataset) {
		JFreeChart jFreeChart = ChartFactory.createPieChart("��ͼ", categorydataset, true, true, false);
		return jFreeChart;
	}

	/**
	 * ���ͼ
	 * 
	 * @param dataset
	 * @return
	 */
	private JFreeChart createGanttChart(CategoryDataset categorydataset) {

		JFreeChart jfreechart = ChartFactory.createBarChart("���ͼ", "������", "������", categorydataset, PlotOrientation.HORIZONTAL, true, true, false);
		jfreechart.setBackgroundPaint(new Color(0xbbbbdd));// ���ñ���ɫ
		CategoryPlot categoryplot = jfreechart.getCategoryPlot();
		// �õ�ͼ���Ա㾫ϸ����
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		// ȡ�ø���ͼ�εķ�Χ������,ָ������
		numberaxis.setTickLabelFont(new Font("����", Font.ITALIC, 18));
		// ���������������,��񣬴�С
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		// �����������Ա�׼����Ϊ��λ
		BarRenderer barrenderer = (BarRenderer) categoryplot.getRenderer();
		// barrenderer��ʾ�õ����е����μ��϶���
		barrenderer.setDrawBarOutline(false);// ����ʾ���ε���߿�
		barrenderer.setMaximumBarWidth(2D);// ����ÿ��ͼ�ε������
		GradientPaint gradientpaint = new GradientPaint(0.0F, 0.0F, Color.red, 0.0F, 0.0F, Color.black);// ���ϵ��½������ɫ
		GradientPaint gradientpaint1 = new GradientPaint(0.0F, 0.0F, Color.green, 0.0F, 0.0F, Color.red);// ���ϵ��½������ɫ
		barrenderer.setSeriesPaint(0, gradientpaint);// ��һ������
		barrenderer.setSeriesPaint(1, gradientpaint1);// �ڶ�������

		return jfreechart;

	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            a dataset.
	 * 
	 * @return A chart.
	 */
	private JFreeChart createXYChart(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart("��ͼ", // title
				"����", // x-axis label
				"��ֵ", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));

		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 * 
	 * @return The dataset.
	 */
	private XYDataset createXYDataset() {

		TimeSeries s1 = new TimeSeries("����1");
		s1.add(new Month(2, 2001), 181.8);
		s1.add(new Month(3, 2001), 167.3);
		s1.add(new Month(4, 2001), 153.8);
		s1.add(new Month(5, 2001), 167.6);
		s1.add(new Month(6, 2001), 158.8);
		s1.add(new Month(7, 2001), 148.3);
		s1.add(new Month(8, 2001), 153.9);
		s1.add(new Month(9, 2001), 142.7);
		s1.add(new Month(10, 2001), 123.2);
		s1.add(new Month(11, 2001), 131.8);
		s1.add(new Month(12, 2001), 139.6);
		s1.add(new Month(1, 2002), 142.9);
		s1.add(new Month(2, 2002), 138.7);
		s1.add(new Month(3, 2002), 137.3);
		s1.add(new Month(4, 2002), 143.9);
		s1.add(new Month(5, 2002), 139.8);
		s1.add(new Month(6, 2002), 137.0);
		s1.add(new Month(7, 2002), 132.8);

		TimeSeries s2 = new TimeSeries("����2");
		s2.add(new Month(2, 2001), 129.6);
		s2.add(new Month(3, 2001), 123.2);
		s2.add(new Month(4, 2001), 117.2);
		s2.add(new Month(5, 2001), 124.1);
		s2.add(new Month(6, 2001), 122.6);
		s2.add(new Month(7, 2001), 119.2);
		s2.add(new Month(8, 2001), 116.5);
		s2.add(new Month(9, 2001), 112.7);
		s2.add(new Month(10, 2001), 101.5);
		s2.add(new Month(11, 2001), 106.1);
		s2.add(new Month(12, 2001), 110.3);
		s2.add(new Month(1, 2002), 111.7);
		s2.add(new Month(2, 2002), 111.0);
		s2.add(new Month(3, 2002), 109.6);
		s2.add(new Month(4, 2002), 113.2);
		s2.add(new Month(5, 2002), 111.6);
		s2.add(new Month(6, 2002), 108.8);
		s2.add(new Month(7, 2002), 101.6);

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(s1);
		dataset.addSeries(s2);

		return dataset;

	}

	@Override
	public PropDto getProp(String key) {
		return defaultCom.getProp(key);
	}

	@Override
	public boolean hasPro(String key) {
		return defaultCom.hasPro(key);
	}

	@Override
	public String getPropValue(String key) {
		return defaultCom.getPropValue(key);
	}

	@Override
	public void setPropValue(String key, String value) {
		defaultCom.setPropValue(key, value);
	}

	@Override
	public ComEum getType() {
		return defaultCom.getType();
	}

	@Override
	public boolean isSelect() {
		return defaultCom.isSelect();
	}

	@Override
	public void setSelect(boolean flag) {
		defaultCom.setSelect(flag);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (isSelect()) {
			defaultCom.paintPoint(g);
		}
	}

	@Override
	public void updatePropsByUI() {
		defaultCom.updatePropsByUI();
	}

	@Override
	public String toString() {
		return defaultCom.toString();
	}

	@Override
	public Map<String, PropDto> listProp() {
		return defaultCom.listProp();
	}

	@Override
	public Element cover2Ele(String name) {
		return defaultCom.cover2Ele(name);
	}

	@Override
	public boolean isUIProp(String name) {
		return defaultCom.isUIProp(name);
	}

	@Override
	public void setParentPnl(IStylePanel parent) {
		defaultCom.setParentPnl(parent);
	}

	@Override
	public IStylePanel getParentPnl() {
		return defaultCom.getParentPnl();
	}

}