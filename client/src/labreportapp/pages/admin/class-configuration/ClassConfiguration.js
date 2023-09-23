import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-class-configuration.css";
import {
  faCogs,
  faLayerGroup,
  faEdit,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Button, Table } from "antd";
import { AdClassCongigurationAPI } from "../../../api/admin/AdClassConfigurationAPI";
import ModalUpdateClassConfiguration from "./modal-update/ModalUpdateClassConfiguration";

const ClassConfiguration = () => {
  const [adClassConfiguration, setAdClassConfiguration] = useState([]);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = () => {
    AdClassCongigurationAPI.getAll().then((response) => {
      setAdClassConfiguration(response.data.data);
      console.log(response.data.data);
    });
  };

  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [classConfigurationSelected, setClassConfigurationSelected] =
    useState(null);
  const handleUpdateClassCongiguration = (item) => {
    setShowUpdateModal(true);
    setClassConfigurationSelected(item);
  };

  const handleModalUpdateCancel = () => {
    setShowUpdateModal(false);
    setClassConfigurationSelected(null);
  };

  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
    },
    {
      title: "Tên cấu hình",
      dataIndex: "tenCauHinh",
      key: "tenCauHinh",
    },
    {
      title: "Chỉ số",
      dataIndex: "chiSo",
      key: "chiSo",
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
        <div className="tittle__category">
          <div>
            {" "}
            {<FontAwesomeIcon icon={faLayerGroup} size="1x" />}
            <span style={{ fontSize: "18px", fontWeight: "500" }}>
              {" "}
              Danh sách cấu hình
            </span>
          </div>
          <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
              onClick={() => setShowUpdateModal(true)}
            >
              <FontAwesomeIcon
                icon={faEdit}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Chỉnh sửa cấu hình
            </Button>
          </div>
        </div>
        <br />
        <Table
          columns={columns}
          dataSource={adClassConfiguration}
          key="stt"
          pagination={false}
          onRow={(record, rowIndex) => ({
            onClick: () => handleUpdateClassCongiguration(record),
          })}
        />
      </div>
      <ModalUpdateClassConfiguration
        loadData={loadData}
        visible={showUpdateModal}
        onCancel={handleModalUpdateCancel}
        classConfiguration={adClassConfiguration}
      />
    </div>
  );
};

export default ClassConfiguration;
