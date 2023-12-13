import { Modal, Input } from "antd";
import "./styleModalBackGroundImage.css";
import "react-toastify/dist/ReactToastify.css";
import { listBackgroundImage } from "../../../../../helper/background";
import { GetProject } from "../../../../../app/reducer/detail-project/DPProjectSlice.reducer";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faImage } from "@fortawesome/free-solid-svg-icons";
import { useAppSelector } from "../../../../../app/hook";
import { getStompClient } from "../../stomp-client-config/StompClientManager";
import Cookies from "js-cookie";

const { TextArea } = Input;

const ModalBackGroundImage = ({ visible, onCancel }) => {
  const listBackground = listBackgroundImage();
  const detailProject = useAppSelector(GetProject);
  const stompClient = getStompClient();

  const changeBackgroundImage = (url) => {
    let obj = {
      projectId: detailProject.id,
      name: url,
      type: "0",
    };
    const bearerToken = Cookies.get("token");
    const headers = {
      Authorization: "Bearer " + bearerToken,
    };
    stompClient.send(
      "/action/update-background-project/" + detailProject.id,
      headers,
      JSON.stringify(obj)
    );
  };

  return (
    <>
      <Modal open={visible} onCancel={onCancel} width={780} footer={null}>
        {" "}
        <div style={{ paddingTop: "0", borderBottom: "1px solid black" }}>
          <span style={{ fontSize: "18px" }}>
            <FontAwesomeIcon icon={faImage} style={{ marginRight: "7px" }} />
            Hình nền
          </span>
        </div>
        <div style={{ marginTop: "15px" }}>
          <div className="div-image-container">
            {listBackground.map((item) => (
              <img
                src={item}
                className="img-content"
                onClick={() => {
                  changeBackgroundImage(item);
                }}
              />
            ))}
          </div>
        </div>
      </Modal>
    </>
  );
};

export default ModalBackGroundImage;
