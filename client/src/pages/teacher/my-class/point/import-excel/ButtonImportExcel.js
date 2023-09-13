import { Button, message } from "antd";
import { useEffect, useState } from "react";
import { TeacherExcelAPI } from "../../../../../api/teacher/point/excel/TeacherExcel.api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUpload } from "@fortawesome/free-solid-svg-icons";
import LoadingIndicatorNoOverlay from "../../../../../helper/loadingNoOverlay";
import { toast } from "react-toastify";
import { TeacherPointAPI } from "../../../../../api/teacher/point/TeacherPoint.api";
import { TeacherStudentClassesAPI } from "../../../../../api/teacher/student-class/TeacherStudentClasses.api";
import { useAppDispatch } from "../../../../../app/hook";
import { SetPoint } from "../../../../../app/teacher/point/tePointSlice.reduce";

const ButtonImportExcel = ({ idClass }) => {
  const dispatch = useAppDispatch();
  const [listStudentClassAPI, setListStudentClassAPI] = useState([]);
  const [downloading, setDownloading] = useState(false);
  const [checkPoint, setCheckPoint] = useState(false);
  const [listPoint, setListPoint] = useState([]);
  const [loadingData, setLoadingData] = useState(false);
  const [inputFile, setInputFile] = useState("");
  const fetchData = async (idClass) => {
    await Promise.all([
      await featchStudentClass(idClass),
      await featchPoint(idClass),
    ]);
  };
  const featchPoint = async (idClass) => {
    try {
      await TeacherPointAPI.getPointByIdClass(idClass).then((response) => {
        if (response.data.data.length >= 1) {
          setCheckPoint(true);
          setListPoint(response.data.data);
          featchStudentPoint(idClass);
        } else {
          setCheckPoint(false);
          featchStudentClass(idClass);
          featchStudentPoint(idClass);
        }
      });
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  const featchStudentClass = async (idClass) => {
    try {
      await TeacherStudentClassesAPI.getStudentInClasses(idClass).then(
        (responese) => {
          const listAPI = responese.data.data.map((item) => {
            return { ...item };
          });
          setListStudentClassAPI(listAPI);
        }
      );
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang");
    }
  };
  const featchStudentPoint = async (idClass) => {
    try {
      if (checkPoint) {
        const listShowTable = listStudentClassAPI.map((item1) => {
          const matchedObject = listPoint.find(
            (item2) =>
              item2.idStudent === item1.idStudent && item2.idClass === idClass
          );
          return {
            ...item1,
            ...matchedObject,
            finalPoint:
              parseFloat(
                parseFloat(matchedObject.checkPointPhase1) +
                  parseFloat(matchedObject.checkPointPhase2)
              ) / 2,
          };
        });
        dispatch(SetPoint(listShowTable));
      } else {
        const listShowTable = listStudentClassAPI.map((item1) => {
          return {
            ...item1,
            idClass: idClass,
            checkPointPhase1: parseFloat("0"),
            checkPointPhase2: parseFloat("0"),
            finalPoint: parseFloat("0"),
          };
        });
        dispatch(SetPoint(listShowTable));
      }
      setLoadingData(true);
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  useEffect(() => {
    if (loadingData === true) {
      fetchData(idClass);
      setTimeout(() => {
        setDownloading(false);
        toast("Import điểm sinh viên thành công !", {
          position: toast.POSITION.TOP_CENTER,
        });
      }, 1200);
    }
  }, [loadingData]);
  const handleFileChange = async (e) => {
    try {
      const selectedFile = e.target.files[0];
      if (selectedFile) {
        const formData = new FormData();
        formData.append("multipartFile", selectedFile);
        await TeacherExcelAPI.import(formData, idClass)
          .then((response) => {
            setDownloading(true);
            if (response.data.data.status === true) {
              fetchData(idClass);
              setInputFile("");
            } else {
              setDownloading(false);
              message.error("Import thất bại, " + response.data.data.message);
            }
            setLoadingData(false);
          })
          .catch((error) => {
            alert("Lỗi hệ thống, vui lòng F5 lại trang !");
          });
      }
    } catch (error) {
      alert("Lỗi hệ thống, vui lòng F5 lại trang !");
    }
  };
  return (
    <>
      {downloading && <LoadingIndicatorNoOverlay />}
      <Button
        style={{
          backgroundColor: "rgb(38, 144, 214)",
          color: "white",
        }}
        onClick={() => {
          document.getElementById("fileInput").click();
        }}
      >
        <FontAwesomeIcon icon={faUpload} style={{ marginRight: "7px" }} />
        {downloading ? (
          "Đang tải lên..."
        ) : (
          <>
            Import bảng điểm
            <input
              id="fileInput"
              type="file"
              accept=".xlsx"
              onChange={handleFileChange}
              style={{
                display: "none",
              }}
            />
          </>
        )}
      </Button>
    </>
  );
};
export default ButtonImportExcel;
