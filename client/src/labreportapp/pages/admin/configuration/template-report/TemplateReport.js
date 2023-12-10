import {
  faFileDownload,
  faHistory,
  faTemperature0,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { AdTemplateReportAPI } from "../../../../api/admin/AdTemplateReportAPI";
import { Button, message } from "antd";
import TextArea from "antd/es/input/TextArea";

import LoadingIndicator from "../../../../helper/loading";
import {
  SetLoadingFalse,
  SetLoadingTrue,
} from "../../../../app/common/Loading.reducer";
import { useAppDispatch } from "../../../../app/hook";
import ModalHistoryTemplateReport from "./modal-history-template-report/ModalHistoryTemplateReport";

const TemplateReport = () => {
  const [templateReport, setTemplateReport] = useState({ descriptions: "" });
  const [edit, setEdit] = useState(false);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    document.title = "Template báo cáo | Lab-Report-App";
  }, []);

  useEffect(() => {
    setIsLoading(true);
    fetchData();
  }, []);

  const fetchData = () => {
    try {
      AdTemplateReportAPI.fetchTemplateReportById().then((reponse) => {
        setTemplateReport(reponse.data.data);
        setIsLoading(false);
      });
    } catch (error) {
      console.log(error);
    }
  };

  const dispatch = useAppDispatch();

  const update = (data) => {
    dispatch(SetLoadingTrue());
    AdTemplateReportAPI.updateTemplateReportById(data).then(
      (res) => {
        message.success("Cập nhật thành công !");
        dispatch(SetLoadingFalse());
      },
      (error) => {}
    );
  };
  const [visibleHistory, setVisibleHistory] = useState(false);
  const openModalShowHistory = () => {
    setVisibleHistory(true);
  };
  const cancelModalHistory = () => {
    setVisibleHistory(false);
  };

  const dowloadLog = () => {
    AdTemplateReportAPI.dowloadLog().then(
      (response) => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const a = document.createElement("a");
        a.href = url;
        a.download = "cau_hinh_template_bao_cao.csv";
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
            <FontAwesomeIcon
              icon={faTemperature0}
              style={{ marginRight: "8px" }}
            />
            Template báo cáo
          </span>
        </div>
      </div>
      <div className="box-general" style={{ paddingTop: 10, marginTop: 0 }}>
        {isLoading && <LoadingIndicator />}
        <div className="box-son-general">
          <div style={{ marginBottom: "15px" }}>
            {edit === false ? (
              <Button
                className="btn_filter"
                onClick={() => setEdit(true)}
                style={{ fontSize: "15px" }}
              >
                Cập nhật
              </Button>
            ) : (
              <Button
                className="btn__clear"
                onClick={() => {
                  update(templateReport);
                  setEdit(false);
                }}
                style={{
                  fontSize: "15px",
                  backgroundColor: "rgb(50, 144, 202)",
                }}
              >
                Xác nhận
              </Button>
            )}
            <div style={{ float: "right" }}>
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
            </div>
          </div>

          <div
            style={{
              width: "100%",
              height: "550px",
            }}
          >
            <TextArea
              rows={4}
              placeholder="Nội dung"
              value={templateReport?.descriptions}
              onChange={(e) =>
                setTemplateReport({
                  ...templateReport,
                  descriptions: e.target.value,
                })
              }
              readOnly={edit === true ? false : true}
              style={{ width: "100%", height: "480px" }}
            />
          </div>
        </div>
      </div>
      <ModalHistoryTemplateReport
        visible={visibleHistory}
        onCancel={cancelModalHistory}
      />
    </>
  );
};

export default TemplateReport;
