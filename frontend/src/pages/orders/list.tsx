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
  IOrderStatus,
} from '../../interfaces';
import { userInfo } from 'os';
const { RangePicker } = DatePicker;

export const OrderList = () => {
  const { token } = theme.useToken();

  const { tableProps, sorters, filters } = useTable<
    IOrder,
    HttpError,
    IOrderFilterVariables
  >({
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
        id: item.id,
        amount: item.amount,
        orderNumber: item.orderNumber,
        status: item.status.text,
        store: item.store.title,
        createdAt: item.store.createdAt,
        user: item.user.firstName,
      };
    },
  });

  const { selectProps: orderSelectProps } = useSelect<IOrderStatus>({
    resource: 'orderStatuses',
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
        rowKey="id"
        style={{
          cursor: 'pointer',
        }}
        onRow={(record) => {
          return {
            onClick: () => {
              show('orders', record.id);
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
          dataIndex="orderNumber"
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
            // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
            <SearchOutlined
              style={{
                color: filtered ? token.colorPrimary : undefined,
              }}
            />
          )}
          defaultFilteredValue={getDefaultFilter('orderNumber', filters, 'eq')}
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

        {/* Filter by Customer ID */}
        <Table.Column<IOrder>
          key="user.id"
          dataIndex={['user', 'id']}
          title={t('orders.fields.customerID')}
          filterDropdown={(props) => (
            <FilterDropdown {...props}>
              <Input placeholder={t('orders.filter.customerId.placeholder')} />
            </FilterDropdown>
          )}
        />
        <Table.Column<IOrder>
          key="products"
          dataIndex="products"
          title={t('orders.fields.products')}
          render={(_, record) => {
            return <OrderTableColumnProducts order={record} />;
          }}
        />
        <Table.Column
          align="right"
          key="amount"
          dataIndex="amount"
          title={t('orders.fields.amount')}
          defaultSortOrder={getDefaultSortOrder('amount', sorters)}
          sorter
          render={(value) => {
            return (
              <NumberField
                options={{
                  currency: 'USD',
                  style: 'currency',
                }}
                value={value}
              />
            );
          }}
        />

        <Table.Column
          key="user.fullName"
          dataIndex={['user', 'fullName']}
          title={t('orders.fields.customer')}
          filterIcon={(filtered) => (
            // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
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
        />
        {/* <Table.Column
          key="createdAt"
          dataIndex="createdAt"
          title={t('orders.fields.createdAt')}
          render={(value) => <DateField value={value} format="LLL" />}
          sorter
        /> */}
        {/* Filter by Sales Date */}
        <Table.Column<IOrder>
          key="createdAt"
          dataIndex="createdAt"
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
                    console.log(dates);
                    // Convert dates to the format you want to use for filtering
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
                  } // Convert the selectedKeys to dayjs objects for RangePicker
                />
              </FilterDropdown>
            );
          }}
          render={(value) => <DateField value={value} />} // Display date in the correct format
        />

        <Table.Column<IOrder>
          key="status.text"
          dataIndex="status"
          title={t('orders.fields.status')}
          render={(status) => {
            return <OrderStatus status={status.text} />;
          }}
          sorter
          defaultSortOrder={getDefaultSortOrder('status.text', sorters)}
          defaultFilteredValue={getDefaultFilter('status.text', filters, 'in')}
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

        <Table.Column<IOrder>
          fixed="right"
          title={t('table.actions')}
          dataIndex="actions"
          key="actions"
          align="center"
          render={(_value, record) => <OrderActions record={record} />}
        />
      </Table>
    </List>
  );
};
