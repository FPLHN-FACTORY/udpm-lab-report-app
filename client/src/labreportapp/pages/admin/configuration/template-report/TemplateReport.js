import { faTemperature0 } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { AdTemplateReportAPI } from "../../../../api/admin/AdTemplateReportAPI";
import { Button, message } from "antd";
import TextArea from "antd/es/input/TextArea";
import { toast } from "react-toastify";
import LoadingIndicator from "../../../../helper/loading";

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

  const update = (data) => {
    AdTemplateReportAPI.updateTemplateReportById(data)
      .then(() => {
        message.success("Cập nhật thành công!");
      })
      .catch(() => {});
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
    </>
  );
};

export default TemplateReport;
