import { DownOutlined, SearchOutlined } from "@ant-design/icons";
import {
  useGetIdentity,
  useGetLocale,
  useList,
  useSetLocale,
  useTranslate,
} from "@refinedev/core";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

import {
  Layout as AntdLayout,
  AutoComplete,
  Avatar,
  Button,
  Col,
  Dropdown,
  Grid,
  Input,
  Row,
  Space,
  theme,
  Typography,
  type MenuProps,
} from 'antd';

import debounce from "lodash/debounce";
import { useTranslation } from "react-i18next";

import { IconMoon, IconSun } from "../../components/icons";
import { useConfigProvider } from "../../context";
import type { ICourier, IIdentity, IOrder, IStore } from "../../interfaces";
import { useStyles } from "./styled";

const { Header: AntdHeader } = AntdLayout;
const { useToken } = theme;
const { Text } = Typography;
const { useBreakpoint } = Grid;

interface IOptionGroup {
  value: string;
  label: string | React.ReactNode;
}

interface IOptions {
  label: string | React.ReactNode;
  options: IOptionGroup[];
}

export const Header: React.FC = () => {
  const { token } = useToken();
  const { styles } = useStyles();
  const { mode, setMode } = useConfigProvider();
  const { i18n } = useTranslation();
  const locale = useGetLocale();
  const changeLanguage = useSetLocale();
  const { data: user } = useGetIdentity<IIdentity>();
  const role = localStorage.getItem("role");
  const screens = useBreakpoint();
  const t = useTranslate();
  const currentLocale = locale();

  const renderTitle = (title: string) => (
    <div className={styles.headerTitle}>
      <Text style={{ fontSize: '16px' }}>{title}</Text>
      <Link to={`/${title.toLowerCase()}`}>{t('search.more')}</Link>
    </div>
  );

  const renderItem = (title: string, imageUrl: string, link: string) => ({
    value: title,
    label: (
      <Link to={link} style={{ display: 'flex', alignItems: 'center' }}>
        {imageUrl && (
          <Avatar
            size={32}
            src={imageUrl}
            style={{ minWidth: '32px', marginRight: '16px' }}
          />
        )}
        <Text>{title}</Text>
      </Link>
    ),
  });

  const [value, setValue] = useState<string>('');
  const [options, setOptions] = useState<IOptions[]>([]);

  const { refetch: refetchOrders } = useList<IOrder>({
    resource: 'orders',
    config: {
      filters: [{ field: 'q', operator: 'contains', value }],
    },
    queryOptions: {
      enabled: false,
      onSuccess: (data) => {
        // const orderOptionGroup = data.data.map((item) =>
        //   renderItem(
        //     `${item.store.title} / #${item.orderNumber}`,
        //     item?.products?.[0].images?.[0]?.url ||
        //       '/images/default-order-img.png',
        //     `/orders/show/${item.id}`
        //   )
        // );
        // if (orderOptionGroup.length > 0) {
        //   setOptions((prevOptions) => [
        //     ...prevOptions,
        //     {
        //       label: renderTitle(t('orders.orders')),
        //       options: orderOptionGroup,
        //     },
        //   ]);
        // }
      },
    },
  });

  const { refetch: refetchStores } = useList<IStore>({
    resource: 'stores',
    config: {
      filters: [{ field: 'q', operator: 'contains', value }],
    },
    queryOptions: {
      enabled: false,
      onSuccess: (data) => {
        // const storeOptionGroup = data.data.map((item) =>
        //   renderItem(item.title, '', `/stores/edit/${item.id}`)
        // );
        // if (storeOptionGroup.length > 0) {
        //   setOptions((prevOptions) => [
        //     ...prevOptions,
        //     {
        //       label: renderTitle(t('stores.stores')),
        //       options: storeOptionGroup,
        //     },
        //   ]);
        // }
      },
    },
  });

  const { refetch: refetchCouriers } = useList<ICourier>({
    resource: 'couriers',
    config: {
      filters: [{ field: 'q', operator: 'contains', value }],
    },
    queryOptions: {
      enabled: false,
      onSuccess: (data) => {
        // const courierOptionGroup = data.data.map((item) =>
        //   renderItem(
        //     `${item.name} ${item.surname}`,
        //     item.avatar[0].url,
        //     `/couriers/show/${item.id}`
        //   )
        // );
        // if (courierOptionGroup.length > 0) {
        //   setOptions((prevOptions) => [
        //     ...prevOptions,
        //     {
        //       label: renderTitle(t('couriers.couriers')),
        //       options: courierOptionGroup,
        //     },
        //   ]);
        // }
      },
    },
  });

  useEffect(() => {
    setOptions([]);
    refetchOrders();
    refetchCouriers();
    refetchStores();
  }, [value]);

  const menuItems: MenuProps['items'] = [...(i18n.languages || [])]
    .sort()
    .map((lang: string) => ({
      key: lang,
      onClick: () => changeLanguage(lang),
      icon: (
        <span style={{ marginRight: 8 }}>
          <Avatar size={16} src={`/images/flags/${lang}.svg`} />
        </span>
      ),
      label: lang === "en" ? "English" : "Grman",
    }));

  return (
    <AntdHeader
      style={{
        backgroundColor: token.colorBgElevated,
        padding: '0 24px',
      }}
    >
      <Row
        align="middle"
        style={{
          justifyContent: screens.sm ? 'space-between' : 'end',
        }}
      >
        <Col xs={0} sm={8} md={12}>
          <AutoComplete
            style={{
              width: '100%',
              maxWidth: '550px',
            }}
            options={options}
            filterOption={false}
            onSearch={debounce((value: string) => setValue(value), 300)}
          >
            <Input
              size="large"
              placeholder={"Search"}
              suffix={<div className={styles.inputSuffix}>/</div>}
              // @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66
              prefix={<SearchOutlined className={styles.inputPrefix} />}
            />
          </AutoComplete>
        </Col>
        <Col>
          <Space size={screens.md ? 32 : 16} align="center">
            <Dropdown
              menu={{
                items: menuItems,
                selectedKeys: currentLocale ? [currentLocale] : [],
              }}
            >
              <Button onClick={(e) => e.preventDefault()}>
                <Space>
                  <Text className={styles.languageSwitchText}>
                    {currentLocale === 'en' ? 'English' : 'German'}
                  </Text>
                  {/* @ts-expect-error Ant Design Icon's v5.0.1 has an issue with @types/react@^18.2.66 */}
                  <DownOutlined className={styles.languageSwitchIcon} />
                </Space>
              </Button>
            </Dropdown>

            <Button
              className={styles.themeSwitch}
              type="text"
              icon={mode === 'light' ? <IconMoon /> : <IconSun />}
              onClick={() => {
                setMode(mode === 'light' ? 'dark' : 'light');
              }}
            />

            <Space size={screens.md ? 16 : 8} align="center">
              <Text ellipsis className={styles.userName}>
                {user?.name} ({role})
              </Text>
              <Avatar size="large" alt={user?.name}>
                {user?.name.charAt(0)}
              </Avatar>
            </Space>
          </Space>
        </Col>
      </Row>
    </AntdHeader>
  );
};
