// import { Button, Spin } from "antd";
// import { useState } from "react";
// import { TeacherExcelTeamAPI } from "../../../../../api/teacher/teams-class/excel/TeacherExcelTeam.api";
// import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
// import { faUpload } from "@fortawesome/free-solid-svg-icons";
// import { toast } from "react-toastify";

// const ButtonImportExcelTeam = ({ idClass }) => {
//   const [downloading, setDownloading] = useState(false);
//   const [inputFile, setInputFile] = useState("");
//   const handleFileChange = async (e) => {
//     try {
//       const selectedFile = e.target.files[0];
//       if (selectedFile) {
//         const formData = new FormData();
//         formData.append("multipartFile", selectedFile);
//         await TeacherExcelTeamAPI.import(formData, idClass).then((response) => {
//           setTimeout(() => {
//             if (response.data.data.status === true) {
//               setInputFile("");
//               toast.success("Đang import, vui lòng chờ giây lát !", {
//                 position: toast.POSITION.TOP_CENTER,
//               });
//             } else {
//               console.log("+++++++++++++++++++");
//               console.log(response);
//               toast.error(
//                 "Import thất bại, " +
//                   response.data.data.message +
//                   ", vui lòng chờ !",
//                 {
//                   position: toast.POSITION.TOP_CENTER,
//                 }
//               );
//             }
//           }, 200);
//           //(true);
//           // setTimeout(() => {
//           //   window.open(
//           //     `http://localhost:3000/teacher/my-class/teams/` + idClass,
//           //     "_self"
//           //   );
//           //   setDownloading(false);
//           // }, 6000);
//         });
//         // .catch((error) => {
//         //   // alert("Lỗi hệ thống, vui lòng F5 lại trang !");
//         //   console.log(error);
//         //   toast.error(error);
//         // });
//       }
//     } catch (error) {
//       alert("Lỗi hệ thống, vui lòng F5 lại trang !");
//     }
//   };
//   return (
//     <>
//       <Spin spinning={downloading}>
//         <Button
//           className="btn_clear"
//           style={{
//             backgroundColor: "rgb(38, 144, 214)",
//             color: "white",
//           }}
//           onClick={() => {
//             document.getElementById("fileInput").click();
//           }}
//         >
//           <FontAwesomeIcon icon={faUpload} style={{ marginRight: "7px" }} />
//           {downloading ? (
//             "Đang tải lên..."
//           ) : (
//             <>
//               Import nhóm
//               <input
//                 id="fileInput"
//                 type="file"
//                 accept=".xlsx"
//                 onChange={handleFileChange}
//                 style={{
//                   display: "none",
//                 }}
//               />
//             </>
//           )}
//         </Button>
//       </Spin>
//     </>
//   );
// };
// export default ButtonImportExcelTeam;
