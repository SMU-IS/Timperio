import {
  useTranslate,
  useExport,
  useNavigation,
  type HttpError,
  getDefaultFilter,
} from '@refinedev/core';
import dayjs from 'dayjs';

import {
  List,
  useTable,
  getDefaultSortOrder,
  DateField,
  NumberField,
  useSelect,
  ExportButton,
  FilterDropdown,
} from '@refinedev/antd';
import { SearchOutlined } from '@ant-design/icons';
import {
  Table,
  Input,
  Select,
  Typography,
  theme,
  InputNumber,
  DatePicker,
} from 'antd';

import {
  OrderStatus,
  OrderActions,
  PaginationTotal,
  OrderTableColumnProducts,
} from '../../components';
import type {
  IOrder,
  IOrderFilterVariables,
  IOrderSalesType,
} from '../../interfaces';

const { RangePicker } = DatePicker;

export const OrderList = () => {
  const { token } = theme.useToken();

  const { tableProps, sorters, filters } = useTable<
    IOrder,
    HttpError,
    IOrderFilterVariables
  >({
    resource: 'purchaseHistory', // Use the endpoint for purchase history
    filters: {
      initial: [
        {
          field: 'user.fullName',
          operator: 'contains',
          value: '',
        },
        {
          field: 'store.title',
          operator: 'contains',
          value: '',
        },
      ],
    },
  });

  const t = useTranslate();
  const { show } = useNavigation();

  const { isLoading, triggerExport } = useExport<IOrder>({
    sorters,
    filters,
    pageSize: 50,
    maxItemCount: 50,
    mapData: (item) => {
      return {
        id: item.salesId, // Adjusted to salesId
        amount: item.totalPrice, // Adjusted to totalPrice
        orderNumber: item.salesId, // Assuming salesId is treated as order number
        status: item.salesType, // Adjusted to salesType
        // store: item.store.title,
        createdAt: item.salesDate, // Adjusted to salesDate
        user: item.customerId,
      };
    },
  });

  const { selectProps: orderSelectProps } = useSelect<IOrderSalesType>({
    resource: 'purchaseHistory',
    optionLabel: 'text',
    optionValue: 'text',
    defaultValue: getDefaultFilter('status.text', filters, 'in'),
  });

  return (
    <List
      headerProps={{
        extra: <ExportButton onClick={triggerExport} loading={isLoading} />,
      }}
    >
      <Table
        {...tableProps}
        rowKey="salesId" // Changed to salesId for row key
        style={{
          cursor: 'pointer',
        }}
        onRow={(record) => {
          return {
            onClick: () => {
              show('orders', record.salesId); // Changed to salesId
            },
          };
        }}
        pagination={{
          ...tableProps.pagination,
          showTotal: (total) => (
            <PaginationTotal total={total} entityName="orders" />
          ),
        }}
      >
        <Table.Column
          key="orderNumber"
          dataIndex="salesId" // Adjusted to salesId
          title={t('orders.fields.order')}
          render={(value) => (
            <Typography.Text
              style={{
                whiteSpace: 'nowrap',
              }}
            >
              #{value}
            </Typography.Text>
          )}
          filterIcon={(filtered) => (
            <SearchOutlined
              style={{
                color: filtered ? token.colorPrimary : undefined,
              }}
            />
          )}
          defaultFilteredValue={getDefaultFilter('salesId', filters, 'eq')} // Changed to salesId
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <InputNumber
                addonBefore="#"
                style={{ width: '100%' }}
                placeholder={t('orders.filter.orderNumber.placeholder')}
              />
            </FilterDropdown>
          )}
        />

        <Table.Column<IOrder>
          key="user.id"
          dataIndex="customerId"
          title={t('orders.fields.customerID')}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Input placeholder={t('orders.filter.customerId.placeholder')} />
            </FilterDropdown>
          )}
        />
        <Table.Column<IOrder>
          key="product"
          dataIndex="product" // Adjusted to access product name directly
          title={t('orders.fields.products')}
          // render={(_, record) => {
          //   return <OrderTableColumnProducts order={record} />;
          // }}
        />
        <Table.Column
          align="right"
          key="amount"
          dataIndex="totalPrice" // Adjusted to totalPrice
          title={t('orders.fields.amount')}
          defaultSortOrder={getDefaultSortOrder('totalPrice', sorters)} // Adjusted to totalPrice
          sorter
          render={(value) => {
            return (
              <NumberField
                options={{
                  currency: 'SGD',
                  style: 'currency',
                }}
                value={value}
              />
            );
          }}
        />

        {/* <Table.Column
          key="user.fullName"
          dataIndex={['user', 'fullName']}
          title={t('orders.fields.customer')}
          filterIcon={(filtered) => (
            <SearchOutlined
              style={{
                color: filtered ? token.colorPrimary : undefined,
              }}
            />
          )}
          defaultFilteredValue={getDefaultFilter(
            'user.fullName',
            filters,
            'contains'
          )}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Input placeholder={t('orders.filter.customer.placeholder')} />
            </FilterDropdown>
          )}
        /> */}

        <Table.Column<IOrder>
          key="salesDate"
          dataIndex="salesDate" // Adjusted to salesDate
          title={t('orders.fields.salesDate')}
          sorter
          filterDropdown={(props) => {
            const { setSelectedKeys, confirm, clearFilters, selectedKeys } =
              props;

            return (
              <FilterDropdown {...props}>
                <RangePicker
                  style={{ width: '100%' }}
                  onChange={(dates) => {
                    setSelectedKeys(
                      dates
                        ? [dates[0].toISOString(), dates[1].toISOString()]
                        : []
                    );
                  }}
                  value={
                    selectedKeys.length
                      ? [dayjs(selectedKeys[0]), dayjs(selectedKeys[1])]
                      : []
                  }
                />
              </FilterDropdown>
            );
          }}
          render={(value) => <DateField value={value} />}
        />

        <Table.Column<IOrder>
          key="salesType"
          dataIndex="salesType" // Adjusted to salesType
          title={t('orders.fields.salesType')}
          render={(salesType) => {
            return <OrderStatus status={salesType} />; // Adjusted to render salesType
          }}
          sorter
          defaultSortOrder={getDefaultSortOrder('salesType', sorters)} // Adjusted to salesType
          defaultFilteredValue={getDefaultFilter('salesType', filters, 'in')}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Select
                {...orderSelectProps}
                style={{ width: '200px' }}
                allowClear
                mode="multiple"
                placeholder={t('orders.filter.status.placeholder')}
              />
            </FilterDropdown>
          )}
        />

        {/* <Table.Column<IOrder>
          fixed="right"
          title={t('table.actions')}
          dataIndex="actions"
          key="actions"
          align="center"
          render={(_value, record) => <OrderActions record={record} />}
        /> */}
      </Table>
    </List>
  );
};
