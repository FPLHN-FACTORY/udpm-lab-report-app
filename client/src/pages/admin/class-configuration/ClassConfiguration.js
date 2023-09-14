import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-class-configuration.css";
import { faCogs, faEdit } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Table, Tooltip } from "antd";
import { AdClassCongigurationAPI } from "../../../api/admin/AdClassConfigurationAPI";
import ModalUpdateClassConfiguration from "./modal-update/ModalUpdateClassConfiguration";

const ClassConfiguration = () => {
  const [classConfiguration, setClassConfiguration] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    AdClassCongigurationAPI.getAll().then((response) => {
      setClassConfiguration(response.data.data);
    });
  };

  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [classConfigurationSelected, setClassConfigurationSelected] =
    useState(null);
  const handleUpdateClassCongiguration = (item) => {
    setShowUpdateModal(true);
    setClassConfigurationSelected(item);
    console.log(item);
  };

  const handleModalUpdateCancel = () => {
    setShowUpdateModal(false);
    setClassConfigurationSelected(null);
  };

  const columns = [
    {
      title: "STT",
      dataIndex: "index",
      key: "index",
      render: (text, record, index) => index + 1,
    },
    {
      title: "Số lượng sinh viên",
      dataIndex: "classSizeMax",
      key: "classSizeMax",
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      render: (text, record) => (
        <div>
          <Tooltip title="Chỉnh sửa chi tiết">
            <FontAwesomeIcon
              icon={faEdit}
              size="1x"
              onClick={() => {
                handleUpdateClassCongiguration(record);
              }}
            />
          </Tooltip>
        </div>
      ),
    },
  ];
  return (
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon icon={faCogs} style={{ marginRight: "8px" }} /> Cấu
          hình lớp học
        </span>
      </div>
      <div className="box-son-general">
        <Table
          dataSource={classConfiguration}
          columns={columns}
          pagination={false}
          key="index"
        />
      </div>
      <ModalUpdateClassConfiguration
        loadData={loadData}
        visible={showUpdateModal}
        onCancel={handleModalUpdateCancel}
        classConfiguration={classConfigurationSelected}
      />
    </div>
  );
};

export default ClassConfiguration;
