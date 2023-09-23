import { faTemperature0 } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useEffect, useState } from "react";
import { AdTemplateReportAPI } from "../../../api/admin/AdTemplateReportAPI";
import { Button, Card, Tooltip } from "antd";
import TextArea from "antd/es/input/TextArea";
import { faPencil, faSave } from "@fortawesome/free-solid-svg-icons";
import { toast } from "react-toastify";

const TemplateReport = () => {
  const [templateReport, setTemplateReport] = useState({ descriptions: "" });
  const [edit, setEdit] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = () => {
    try {
      AdTemplateReportAPI.fetchTemplateReportById().then((reponse) => {
        setTemplateReport(reponse.data.data);
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
    <div className="box-general">
      <div className="heading-box">
        <span style={{ fontSize: "20px", fontWeight: "500" }}>
          <FontAwesomeIcon
            icon={faTemperature0}
            style={{ marginRight: "8px" }}
          />
          Template Báo Cáo
        </span>
      </div>
      <div className="box-son-general">
        <Card
          style={{
            width: "100%",
            height: "550px",
            boxShadow: "2px 2px 2px 2px rgb(226, 179, 87)",
          }}
          key={templateReport?.id}
          title={"Báo cáo"}
          extra={
            edit === false ? (
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
              // <Tooltip title="Cập nhật">
              //   <FontAwesomeIcon
              //     onClick={() => setEdit(true)}
              //     style={{ fontSize: "15px" }}
              //     icon={faPencil}
              //     size="1x"
              //   />
              // </Tooltip>
              // <Tooltip title="Lưu">
              //   <FontAwesomeIcon
              //     onClick={() => {
              //       update(templateReport);
              //       setEdit(false);
              //     }}
              //     style={{ fontSize: "15px" }}
              //     icon={faSave}
              //     size="1x"
              //   />
              // </Tooltip>
            )
          }
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
        </Card>
      </div>
    </div>
  );
};

export default TemplateReport;
