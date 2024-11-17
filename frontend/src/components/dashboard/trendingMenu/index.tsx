import { Table, Typography, Spin } from "antd";
import { Statistic } from "antd/lib";
import axios from "axios";
import dayjs from "dayjs";
import { useEffect, useState } from "react";
import { formatWithoutDollarSign } from "../../../helper";

interface DateRangePickerProps {
  selectedDateRange: { start: string; end: string };
  height: number;
}

export const TrendingMenu = ({
  selectedDateRange,
  height,
}: DateRangePickerProps) => {
  const [data, setData] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);

  const fetchTrendingData = async (start: dayjs.Dayjs, end: dayjs.Dayjs) => {
    setLoading(true);
    try {
      const response = await axios.get(
        `${import.meta.env.VITE_SERVER}/api/v1/purchaseHistory`,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token_timperio")}`,
          },
        }
      );

      const aggregatedData = response.data.reduce(
        (acc: any[], product: any) => {
          const productDate = dayjs(product.salesDate);
          if (
            (start && productDate.isBefore(start, "day")) ||
            (end && productDate.isAfter(end, "day"))
          ) {
            return acc;
          }

          const existingProduct = acc.find(
            (entry) => entry.product === product.product // Update to check `product` field
          );

          if (existingProduct) {
            existingProduct.count += 1;
          } else {
            acc.push({
              product: product.product, // Store product name
              count: 1,
            });
          }
          return acc;
        },
        []
      );

      const topProducts = aggregatedData
        .sort((a, b) => b.count - a.count) // Sort by highest count
        .slice(0, 5); // Get top 5 products

      setData(topProducts); // Set the top products to state
    } catch (error) {
      console.error("Error fetching trending data:", error);
    } finally {
      setLoading(false);
    }
  };

  // Effect hook to fetch data when selectedDateRange changes
  useEffect(() => {
    const start = dayjs(selectedDateRange.start);
    const end = dayjs(selectedDateRange.end);
    fetchTrendingData(start, end);
  }, [selectedDateRange]);

  // Define the columns for the Ant Design Table
  const columns = [
    {
      title: "Product",
      dataIndex: "product",
      key: "product",
    },
    {
      title: "Qty",
      dataIndex: "count",
      key: "count",
      align: "center",
      render: (count: number) => (
        <Statistic value={count} valueStyle={{ fontSize: "14px" }} /> // Using Statistic for Qty
      ),
    },
  ];

  const dataSource = data.map((product, index) => ({
    key: index, // Unique key required by Ant Design
    product: product.product,
    count: product.count,
  }));

  return (
    <div>
      {loading ? (
        <div style={{ textAlign: "center", padding: "20px" }}>
          <Spin size="large" /> {/* Ant Design spinner */}
        </div>
      ) : data.length === 0 ? (
        <div style={{ padding: 20, textAlign: "center" }}>
          No data available for the selected range.
        </div>
      ) : (
        <Table
          dataSource={dataSource}
          columns={columns}
          pagination={false} // Optional: Disable pagination for small datasets
          style={{ margin: "10px 20px" }} // Optional: Adjust table margins
        />
      )}
    </div>
  );
};
