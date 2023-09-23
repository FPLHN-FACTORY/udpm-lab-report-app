import { Tooltip } from "antd";
import "./styleImage.css";
import { memo } from "react";
import { Avartar } from "../../assets/img/anh1.jpg";

const Image = ({ url, picxel, marginRight, name }) => {
  return (
    <Tooltip title={name}>
      <img
        src={
          url === "Images/Default.png"
            ? "https://raw.githubusercontent.com/FPLHN-FACTORY/udpm-portal-projects/develop/front_end_reactJS/src/assets/img/anh1.jpg"
            : url
        }
        style={{
          width: picxel + "px",
          height: picxel + "px",
          marginRight: marginRight + "px",
        }}
        className="image_common"
        alt="."
      />
    </Tooltip>
  );
};

export default memo(Image);
