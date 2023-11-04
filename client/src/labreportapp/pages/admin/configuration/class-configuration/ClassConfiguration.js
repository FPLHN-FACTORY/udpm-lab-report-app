import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-class-configuration.css";
import {
  faCogs,
  faLayerGroup,
  faEdit,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Button, Table } from "antd";
import { AdClassCongigurationAPI } from "../../../../api/admin/AdClassConfigurationAPI";
import ModalUpdateClassConfiguration from "./modal-update/ModalUpdateClassConfiguration";
import LoadingIndicator from "../../../../helper/loading";

const ClassConfiguration = () => {
  const [adClassConfiguration, setAdClassConfiguration] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    loadData();
    document.title = "Cấu hình lớp học | Lab-Report-App";
  }, []);

  const loadData = () => {
    AdClassCongigurationAPI.getAll().then((response) => {
      setAdClassConfiguration(response.data.data);
      
      setIsLoading(false);
    });
  };

  const [showUpdateModal, setShowUpdateModal] = useState(false);
  const [classConfigurationSelected, setClassConfigurationSelected] =
    useState(null);
  const handleUpdateClassCongiguration = (item) => {
    setShowUpdateModal(true);
    setClassConfigurationSelected(item);
  };

  const [isLoading, setIsLoading] = useState(false);

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
    <>
      <div className="box-one">
        <div
          className="heading-box"
          style={{ fontSize: "18px", paddingLeft: "20px" }}
        >
          <span style={{ fontSize: "20px", fontWeight: "500" }}>
            <FontAwesomeIcon icon={faCogs} style={{ marginRight: "8px" }} /> Cấu
            hình lớp học
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        {" "}
        {isLoading && <LoadingIndicator />}
        <div className="box-son-general" style={{ paddingTop: 20 }}>
          <div className="tittle__category">
            <div>
              {" "}
              {
                <FontAwesomeIcon
                  icon={faLayerGroup}
                  style={{ fontSize: "20px", marginRight: "7px" }}
                />
              }
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
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    fontSize: "17px",
                    marginRight: "5px",
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
          />
        </div>
        <ModalUpdateClassConfiguration
          loadData={loadData}
          visible={showUpdateModal}
          onCancel={handleModalUpdateCancel}
          classConfiguration={adClassConfiguration}
        />
      </div>
    </>
  );
};

export default ClassConfiguration;
