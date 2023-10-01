import { faTemperature0 } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { AdTemplateReportAPI } from "../../../api/admin/AdTemplateReportAPI";
import { Button, Card, Tooltip } from "antd";
import TextArea from "antd/es/input/TextArea";
import { faPencil, faSave } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../helper/loading";

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
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };

  const update = (data) => {
    AdTemplateReportAPI.updateTemplateReportById(data)
      .then(() => {
        toast.success("Cập nhật thành công!");
      })
      .catch(() => {
        toast.error("Cập nhật thất bại!");
      });
  };

  return (
    <div className="box-general" style={{ paddingTop: 50 }}>
      {isLoading && <LoadingIndicator />}
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon
            icon={faTemperature0}
            style={{ marginRight: "8px" }}
          />
          Template báo cáo
        </span>
      </div>
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
  );
};

export default TemplateReport;
