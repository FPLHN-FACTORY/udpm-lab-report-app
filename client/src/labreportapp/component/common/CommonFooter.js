import { FloatButton } from "antd";
import LogoFactory from "../../assets/img/logo_bit_1.png";

const CommonFooter = () => {
  return (
    <>
      {" "}
      <FloatButton.Group
        shape="circle"
        style={{
          bottom: 20,
          right: 20,
        }}
      >
        <FloatButton.BackTop visibilityHeight={0} />
      </FloatButton.Group>
      <div
        style={{
          justifyContent: "center",
          color: "gray",
          display: "flex",
          alignItems: "center",
        }}
      >
        <img
          src={LogoFactory}
          style={{
            width: 45,
            height: 45,
            borderRadius: "50%",
          }}
        />{" "}
        <i>Copyright © 2023 by Poly Business Information Technology Hanoi.</i>
      </div>
      <div
        style={{
          marginLeft: 7,
          marginBottom: 25,
          justifyContent: "center",
          display: "flex",
          alignItems: "center",
        }}
      >
        <img
          src={LogoFactory}
          style={{
            width: 30,
            height: 30,
            borderRadius: "50%",
          }}
        />{" "}
        <span>v1.1</span>
      </div>
    </>
  );
};

export default CommonFooter;
