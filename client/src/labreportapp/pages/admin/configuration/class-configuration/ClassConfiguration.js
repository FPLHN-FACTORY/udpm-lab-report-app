import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./style-class-configuration.css";
import {
  faCogs,
  faLayerGroup,
  faEdit,
  faFileDownload,
  faHistory,
} from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { Button, Table } from "antd";
import { AdClassCongigurationAPI } from "../../../../api/admin/AdClassConfigurationAPI";
import ModalUpdateClassConfiguration from "./modal-update/ModalUpdateClassConfiguration";
import LoadingIndicator from "../../../../helper/loading";
import ModalShowHistoryConfigurationClass from "./modal-show-history-configuration-class/ModalShowHistoryConfigurationClass";

const ClassConfiguration = () => {
  const [adClassConfiguration, setAdClassConfiguration] = useState([]);

  useEffect(() => {
    setIsLoading(true);
    window.scrollTo(0, 0);
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
      title: "Giá trị",
      dataIndex: "chiSo",
      key: "chiSo",
    },
  ];

  const [visibleHistory, setVisibleHistory] = useState(false);
  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };
  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const dowloadLog = () => {
    AdClassCongigurationAPI.dowloadLog().then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "cau_hinh_lop_hoc.csv";
        a.click();
        window.URL.revokeObjectURL(url);
      },
      (error) => {}
    );
  };

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
                  marginRight: 5,
                }}
                onClick={dowloadLog}
              >
                <FontAwesomeIcon
                  icon={faFileDownload}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Dowload log
              </Button>
              <Button
                style={{
                  color: "white",
                  backgroundColor: "rgb(55, 137, 220)",
                  marginRight: 5,
                }}
                onClick={openModalShowHistory}
              >
                <FontAwesomeIcon
                  icon={faHistory}
                  size="1x"
                  style={{
                    backgroundColor: "rgb(55, 137, 220)",
                    marginRight: "5px",
                  }}
                />
                Lịch sử
              </Button>
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
          <div style={{ marginTop: 15 }}>
            <span style={{ color: "red" }}>
              (*) Lưu ý: Số lượng mật ong cấu hình là số lượng mật ong trên{" "}
              <b>1 điểm</b> mà sinh viên đó đạt được khi kết thúc quá trình học
              ở lớp xưởng thực hành.
            </span>
          </div>
        </div>
        <ModalUpdateClassConfiguration
          loadData={loadData}
          visible={showUpdateModal}
          onCancel={handleModalUpdateCancel}
          classConfiguration={adClassConfiguration}
        />
        <ModalShowHistoryConfigurationClass
          visible={visibleHistory}
          onCancel={cancelModalHistory}
        />
      </div>
    </>
  );
};

export default ClassConfiguration;
