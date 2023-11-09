import LogoFactory from "../../assets/img/logo_bit_1.png";

const CommonFooter = () => {
  return (
    <>
      <div
        style={{
          justifyContent: "center",
          color: "gray",
          marginBottom: 25,
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
    </>
  );
};

export default CommonFooter;
